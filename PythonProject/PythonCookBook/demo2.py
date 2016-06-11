#collections.deque的应用
from collections import deque
def search (lines, pattern, history = 5):
    previous_lines = deque(maxlen=history)
    for line in lines:
        if pattern in line:
            print('pattern in line:'+line)
            yield line,previous_lines
        print('pattern not in line:' + line)
        previous_lines.append(line)

if __name__ == '__main__':
    with open('test.html') as f:
        for line, prevlinews in search(f, 'python', 5):
            for pline in prevlinews:
                print(pline,end =' ')
            print(line,end ='')
            print('-'*20)