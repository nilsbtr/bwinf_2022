import os
import sys
import numpy as np
import copy


class Task:
    def __init__(self, start, time):
        self.start = start
        self.time = time
        self.waited = 0

    def get_start(self):
        return self.start

    def get_time(self):
        return self.time

    def get_waited(self):
        return self.waited

    def progress(self):
        self.time -= 1
        return self.time == 0

    def wait(self):
        self.waited += 1


def main(filename):
    tasks = []
    with open(os.path.join(sys.path[0], filename), 'r', encoding='utf8') as file:
        for line in file:
            line = line.strip()
            if line.strip():
                tasks.append(Task(int(line.split()[0]), int(line.split()[1])))
    print('-- Simulation 1')
    sim1(copy.deepcopy(tasks))
    print('-- Simulation 2')
    sim2(copy.deepcopy(tasks))
    print('-- Simulation 3')
    sim3(copy.deepcopy(tasks))


def sim1(queue):
    total = 0

    for task in queue[:-1]:
        total += task.get_time()

    print('Average:', round(total / len(queue)))
    print('Maximum:', total)


def sim2(queue):
    current = None
    wait = []
    done = []
    time = 0

    while wait or queue:
        time += 1
        if wait and current == None:
            new = find_task(wait)
            current = wait.pop(new)
        while queue and queue[0].get_start() == time:
            if current != None:
                wait.append(current)
                wait.append(queue.pop(0))
                new = find_task(wait)
                current = wait.pop(new)
            else:
                wait.append(queue.pop(0))
                new = find_task(wait)
                current = wait.pop(new)
        for task in wait:
            task.wait()
        if current != None and current.progress():
            done.append(current)
            current = None
    print('Average:', round(np.average([task.get_waited() for task in done])))
    print('Maximum:', max(task.get_waited() for task in done))


def find_task(list):
    low = [0, list[0].time]
    for index, task in enumerate(list):
        if task.time < low[1]:
            low[0], low[1] = index, task.time
    return low[0]


def sim3(queue):
    pass


if __name__ == "__main__":
    for i in range(5):
        print(f'// fahrradwerkstatt{i}.txt')
        main(f'fahrradwerkstatt{i}.txt')
        print('-------------------------')
