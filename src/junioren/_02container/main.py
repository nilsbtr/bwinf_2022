import os
import sys


def main(filename):
    container = []
    with open(os.path.join(sys.path[0], filename), 'r') as file:
        for line in file:
            line = int(line.split()[0])
            if line not in container:
                container.append(line)
        file.seek(0)
        for line in file:
            line = int(line.split()[1])
            if line in container:
                container.remove(line)
    print(container)


if __name__ == "__main__":
    # main(f'container0.txt')
    for i in range(5):
        main(f'container{i}.txt')
