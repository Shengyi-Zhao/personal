class Solution {
    public String longestPalindrome(String s) {
        boolean huiwen=true;
        int le=s.length();
        char a[]=s.toCharArray();
        if (le==1){
            return s;
        }else {
            for(int i=0;i<le+1/2;i++){
                if(s.charAt(i)!=s.charAt(le-1-i)){
                    huiwen=false;
                }
            }
            if(huiwen){
                return s;
            }else {
                if(longestPalindrome(s.substring(0,le-1)).length()>=longestPalindrome(s.substring(1,le)).length()){
                    return longestPalindrome(s.substring(0,le-1));
                }else {
                    return longestPalindrome(s.substring(1,le));
                }
            }
        }
    }
}