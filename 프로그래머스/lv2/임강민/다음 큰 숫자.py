def solution(n):
    i = n
    tmp = i
    while i == n:
        tmp += 1
        if bin(n).count('1') == bin(tmp).count('1'):
            return tmp
