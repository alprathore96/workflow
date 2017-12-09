package com.testing.commons;

public class Utils {
    public static SubstringIndex extractFirstKey(String from) {
        if ( !from.contains(".") ) {
            return new SubstringIndex(from, from.length() - 1);
        }
        int index = from.indexOf(".");
        return new SubstringIndex( from.substring(0, index), index );
    }

    public static SubstringIndex extractFirstKeyFromExpression(String from) {
        if ( ! from.contains("(") ) {
            return extractFirstKey(from);
        }
        int open;
        open = 0;
        char[] chars = from.toCharArray();
        int i = 0;
        for ( char ch : chars ) {
            if ( ch == '(') {
                open++;
            } else if ( ch == ')') {
                open--;
            } else if ( ch == '.' && open == 0) {
                return new SubstringIndex( from.substring(0, i), i);
            }
            i++;
        }
        return new SubstringIndex(from, from.length() - 1);
    }

    public static class SubstringIndex {
        private String substring;
        private int index;

        public SubstringIndex(String substring, int index) {
            this.substring = substring;
            this.index = index;
        }

        public String getSubstring() {
            return substring;
        }

        public void setSubstring(String substring) {
            this.substring = substring;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }
}
