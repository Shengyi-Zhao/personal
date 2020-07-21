'''
group 23
'''

import sys 
sys.path.append("..") 
from app import db, app, db_aurin
from flask import jsonify

'''
Task : Tweets of various categories account for the proportion of total tweets.
Category: Sentiment,
          Time period, 
          Covid, 
          Food
'''
# Sentiment
@app.route('/sentiment_composition',endpoint="sentiment_composition")
def sentiment_composition():
    rows = []; cols = []
    for item in db.view('sentiment/new-view', group=True, group_level=1):
        if item.key is not None:
            rows.append({'c':[{'v':item.key},{'v':item.value}]})

    pairs=[('','Tweet', 'string'), ('','Number', 'number')]
    for pair in pairs:
        cols.append({'id': pair[0], 'label': pair[1], 'type': pair[2]})
    response = {
        'cols': cols,
        'rows': rows
    }
    return jsonify(response)

# FOOD
@app.route('/food_city',endpoint="food_city")
def food_city():
    rows = []; col=[]
    for item in db.view('views/food_view',group=True, group_level=1) :
        rows.append({'c':[{'v':" ".join(item.key)},{'v':item.value}]})

    pairs=[('','Tweet', 'string'), ('','tweet number', 'number')]
    for pair in pairs:
        col.append({'id': pair[0], 'label': pair[1], 'type': pair[2]})
    response = {
        'cols': col,
        'rows': rows
    }
    return jsonify(response)

# TIME
@app.route('/time_category',endpoint="time_category")
def time_category():
    rows=[]; col=[]
    for item in db.view('general_views/time_view',group=True, group_level=1):
        rows.append({'c':[{'v':" ".join(item.key)},{'v':item.value}]})

    pairs=[('','Tweet', 'string'), ('','tweet number', 'number')]
    for pair in pairs:
        col.append({'id': pair[0], 'label': pair[1], 'type': pair[2]})
    response = {
        'cols': col,
        'rows': rows
    }
    return jsonify(response)

'''
Task : In various cities, people pay attention to each category
Category: 
    Sentiment: The ratio of 3 types of sentiment to the total tweets in the city.
    Time: The ratio of 4 time periods to the total tweets in the city.
    Covid: The ratio of covid topics to the total tweets in the city.
    Food: The ratio of food-related topics to the total tweets in the city.
          The ratio of HealthyFood and UnhealthyFood topics to the total tweets in the city.

'''
#Sentiment
@app.route('/sentiment_ratio',endpoint="sentiment_ratio")
def sentiment_ratio():
    df={}; rows=[]; cols=[]
    for item in db.view('views/sentiment_view', group=True, group_level=2, stale="update_after"):
        city = item.key[0]; category = item.key[1]; value = item.value
        if city != 'others':
            if category != None:
                if city not in df:
                    df[city] = [value]
                else:
                    df[city] += [value]

    for key,value in df.items():
        total = value[0] + value[1] + value[2]
        negative_ratio = value[0]/total
        #negative_r = '{:.0f}%'.format(negative_ratio*100)
        neutral_ratio = value[1]/total
        #neutral_r = '{:.0f}%'.format(neutral_ratio*100)
        positive_ratio = value[2]/total
        #positive_r = '{:.0f}%'.format(positive_ratio*100)
        rows.append({'c':[{'v':key}, {'v':negative_ratio},
                          {'v':neutral_ratio}, 
                          {'v':positive_ratio}]})

    pairs=[('','Tweet', 'string'), ('','Negative', 'number'), 
           ('','Neutral', 'number'), 
           ('','Positive','number')]
    for pair in pairs:
        cols.append({'id': pair[0], 'label': pair[1], 'type': pair[2]})
    response = {
        'cols': cols,
        'rows': rows
    }
    return jsonify(response)

#Time
@app.route('/time_ratio',endpoint="time_ratio")
def time_ratio():
    df={}; rows=[]; cols=[]
    for item in db.view('views/time_view', group=True, group_level=2, stale="update_after"):
        city = item.key[0]; category = item.key[1]; value = item.value
        if category != None:
            if city not in df:
                df[city] = [value]
            else:
                df[city] += [value]

    for key,value in df.items():
        total = value[0] + value[1] + value[2] + value[3]
        afternoon_ratio = value[0]/total
        evening_ratio = value[1]/total
        morning_ratio = value[2]/total
        night_ratio = value[3]/total
        rows.append({'c':[{'v':key}, {'v':morning_ratio},{'v':afternoon_ratio}, {'v':evening_ratio}, {'v':night_ratio}]})

    pairs=[('','Tweet', 'string'), ('','Morning', 'number'), ('','Afternoon', 'number'), ('','Evening','number'), ('','Night','number')]
    for pair in pairs:
        cols.append({'id': pair[0], 'label': pair[1], 'type': pair[2]})
    response = {
        'cols': cols,
        'rows': rows
    }
    return jsonify(response)


'''
Caculate total number of tweets in various cities
'''
def total_tw():
    sentiment_dict = {}
    for item in db.view('views/sentiment_view', group=True, group_level=1):
        city = " ".join(item.key)
        sentiment_dict[city] = item.value
    return sentiment_dict

# Covid-19
@app.route('/covid_ratio',endpoint="covid_ratio")
def covid_ratio():
    df={}; rows=[]; cols=[]
    for item in db.view('views/covid-19', group=True, group_level=2, stale="update_after"):
        city = item.key[0]; category = item.key[1]; value = item.value
        if category == 'covid-19':
            df[city] = value

    for key,value in df.items():
        for k,v in total_tw().items():
            if key == k: 
                covid_ratio = value/v
                rows.append({'c':[{'v':key}, {'v':covid_ratio}]})

    pairs=[('','Tweet', 'string'), ('','COVID-19', 'number')]
    for pair in pairs:
        cols.append({'id': pair[0], 'label': pair[1], 'type': pair[2]})
    response = {
        'cols': cols,
        'rows': rows
    }
    return jsonify(response)

# Food
@app.route('/food_ratio',endpoint="food_ratio")
def food_ratio():
    rows=[]; cols=[]
    for item in db.view('views/food_view', group=True, group_level=1):
        city = item.key[0]; value = item.value
        for k,v in total_tw().items():
            if city == k: 
                food_ratio = value/v
                rows.append({'c':[{'v':city}, {'v':food_ratio}]})

    pairs=[('','Tweet', 'string'), ('','Food', 'number')]
    for pair in pairs:
        cols.append({'id': pair[0], 'label': pair[1], 'type': pair[2]})
    response = {
        'cols': cols,
        'rows': rows
    }
    return jsonify(response)

# HealthyFood and UnhealthyFood
@app.route('/food_category_ratio',endpoint="food_category_ratio")
def food_category_ratio():
    df={}; rows=[]; cols=[]
    for item in db.view('views/food_view', group=True, group_level=2, stale="update_after"):
        city = item.key[0]; category = item.key[1]; value = item.value
        if category != None:
            if city not in df:
                df[city] = [value]
            else:
                df[city] += [value]

    for key,value in df.items():
        total = value[0] + value[1]
        healthy_ratio = value[0]/total
        #healthy_r = '{:.0f}%'.format(healthy_ratio*100)
        unhealthy_ratio = value[1]/total
        #unhealthy_r = '{:.0f}%'.format(unhealthy_ratio*100)
        rows.append({'c':[{'v':key}, {'v':healthy_ratio}, {'v':unhealthy_ratio}]})

    pairs=[('','Tweet', 'string'), ('','Healthy Food', 'number'),  ('','Unhealthy Food','number')]
    for pair in pairs:
        cols.append({'id': pair[0], 'label': pair[1], 'type': pair[2]})
    response = {
        'cols': cols,
        'rows': rows
    }
    return jsonify(response)

# Food composition percentage
@app.route('/food_type_ratio',endpoint="food_type_ratio")
def food_type_ratio():
    rows = []; cols = []
    for item in db.view('general_views/food_view', group=True, group_level=1, stale="update_after"):
        rows.append({'c':[{'v':" ".join(item.key)},{'v':item.value}]})

    pairs=[('','Food Type', 'string'), ('','Amount', 'number')]
    for pair in pairs:
        cols.append({'id': pair[0], 'label': pair[1], 'type': pair[2]})
    response = {
        'cols': cols,
        'rows': rows
    }
    return jsonify(response)


def total_tw_food():
    food_dict = {}
    for item in db.view('views/food_view', group=True, group_level=1, stale="update_after"):
        city = " ".join(item.key)
        food_dict[city] = item.value
    return food_dict

# Food composition percentage in Adelaide
@app.route('/Adelaide_ratio',endpoint="Adelaide_ratio")
def Adelaide_ratio():
    rows=[]; cols=[]; total_num_food=0
    for k,v in total_tw_food().items():
        total_num_food += v

    for item in db.view('views/food_view', group=True, group_level=3):
        if item.key[0] == 'Adelaide':
            for city,v in total_tw_food().items():
                if city == item.key[0]:
                    ratio = item.value/v
                    rows.append({'c':[{'v':item.key[2]},{'v':ratio}]})

    pairs=[('','Food Type', 'string'), ('','Food Type Proportion in Adelaide', 'number')]
    for pair in pairs:
        cols.append({'id': pair[0], 'label': pair[1], 'type': pair[2]})
    response = {
        'cols': cols,
        'rows': rows
    }
    return jsonify(response)


'''
AURIN DATA:
Category: 1.Age 2.Average_income 3.Employment_rate 4.Occupation 5.Population
'''
# Age
@app.route('/ages',endpoint="ages")
def ages():
    df={}; rows=[]; cols=[]
    for item in db_aurin.view('views/ages', group=True, group_level=2):
        city = item.key[0]; category = item.key[1]; value = item.value
        if category != None:
            if city not in df:
                df[city] = [value]
            else:
                df[city] += [value]

    for key,value in df.items():
        total = value[0] + value[1] + value[2] + value[3] + value[4]
        age_9 = value[0]/total
        age_19 = value[1]/total
        age_39 = value[2]/total
        age_59 = value[3]/total
        age_60 = value[4]/total
        rows.append({'c':[{'v':key}, {'v':age_9}, {'v':age_19}, {'v':age_39}, {'v':age_59}, {'v':age_60}]})

    pairs=[('','Age','string'), ('','0-9','number'), ('','10-19','number'), ('','20-39','number'), ('','40-59','number'), ('','over60','number')]
    for pair in pairs:
        cols.append({'id': pair[0], 'label': pair[1], 'type': pair[2]})
    response = {
        'cols': cols,
        'rows': rows
    }
    return jsonify(response)

# Average_income
@app.route('/average_income',endpoint="average_income")
def average_income():
    rows = []; cols = []
    for item in db_aurin.view('views/average_income', group=True, group_level=1):
        rows.append({'c':[{'v':" ".join(item.key)},{'v':item.value}]})

    pairs=[('','Average_income', 'string'), ('','AUD', 'number')]
    for pair in pairs:
        cols.append({'id': pair[0], 'label': pair[1], 'type': pair[2]})
    response = {
        'cols': cols,
        'rows': rows
    }
    return jsonify(response)

# Employment_rate
@app.route('/employment_rate',endpoint="employment_rate")
def employment_rate():
    df={}; rows = []; cols = []
    for item in db_aurin.view('views/employment_rate', group=True, group_level=2):
        city = item.key[0]; category = item.key[1]; value = item.value
        if category != None:
            if city not in df:
                df[city] = [value]
            else:
                df[city] += [value]

    for key,value in df.items():
        total = value[0] + value[1] + value[2]
        fulltime = value[0]/total
        parttime = value[1]/total
        un = value[2]/total
        rows.append({'c':[{'v':key}, {'v':fulltime}, {'v':parttime}, {'v':un}]})

    pairs=[('','employment_rate', 'string'), ('','employment full time Proportion', 'number'),
           ('','employment part time Proportion', 'number'),
           ('','unemployed Proportion', 'number')]
    for pair in pairs:
        cols.append({'id': pair[0], 'label': pair[1], 'type': pair[2]})
    response = {
        'cols': cols,
        'rows': rows
    }
    return jsonify(response)

# Occupation
@app.route('/occupation',endpoint="occupation")
def occupation():
    df={}; rows = []; cols = []
    for item in db_aurin.view('views/occupation', group=True, group_level=2):
        city = item.key[0]; category = item.key[1]; value = item.value
        if category != None:
            if city not in df:
                df[city] = [value]
            else:
                df[city] += [value]

    for key,value in df.items():
        total = value[0] + value[1] + value[2]
        Mental_worker = value[0]/total
        Physical_worker = value[1]/total
        Service_worker = value[2]/total
        rows.append({'c':[{'v':key}, {'v':Mental_worker}, {'v':Physical_worker}, {'v':Service_worker}]})

    pairs=[('','Occupation', 'string'), ('','Mental worker', 'number'), ('','Physical worker', 'number'), ('','Service worker', 'number')]
    for pair in pairs:
        cols.append({'id': pair[0], 'label': pair[1], 'type': pair[2]})
    response = {
        'cols': cols,
        'rows': rows
    }
    return jsonify(response)

# Population
@app.route('/population',endpoint="population")
def population():
    rows = []; cols = []
    for item in db_aurin.view('views/population', group=True, group_level=1):
        rows.append({'c':[{'v':" ".join(item.key)},{'v':item.value}]})

    pairs=[('','Population', 'string'), ('','Amount', 'number')]
    for pair in pairs:
        cols.append({'id': pair[0], 'label': pair[1], 'type': pair[2]})
    response = {
        'cols': cols,
        'rows': rows
    }
    return jsonify(response)


'''
Google map shows collected tweets's geolocation
'''
# Australia cities coodinates
@app.route('/city_coordinate')
def city_coordinate():
    tweets_list = [
        [-33.868,151.207,"Sydney"],
        [-37.814,144.963,"Melbourne"],
        [-27.468,153.028,"Brisbane"],
        [-31.952,115.861,"Perth"],
        [-34.929,138.599,"Adelaide"],
        [-35.283,149.128,"Canberra"],
        [-42.879,147.329,"Hobart"],
        [-12.4634,130.8456,"Darwin"]
    ]
    # tweets_list = [[-25.344,131.036,"11111111111"]]
    data = {
        'tweets': tweets_list
    }
    return jsonify(data)

# collected tweets's coordinates
@app.route('/map_data',endpoint="map_data")
def map_data():
    rows = []
    for item in db.view('coordinates/sentiment'):
        for each in item.key[:-1]:
            rows.append([each[0],each[1],item.key[-1]])          

    data = {
        'tweets': rows
    }
    return jsonify(data)

