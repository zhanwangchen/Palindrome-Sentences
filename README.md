# Palindrome-Sentences
Detect Palindrome Sentences Flink implementation



A palindrome sentence has the same order of alphanumeric characters when read backwards. For example:
was it a car or a cat I saw
or
23 yo banana boy 32.

If the given input text is

abc cba 33
 abc abc cba cba 
4 qwer fg gf rewq 4
a qder fg gf redq a
abcde abcde edcba edcba 33     
then the output is a Set<String> with the following two elements:

4 qwer fg gf rewq 4
a qder fg gf redq a
