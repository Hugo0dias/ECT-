time = int(input("Tempo em segundos: "))

hours = time // 60**2
time = time % 60**2
minutes = time // 60
time = time % 60

print("{:02d}:{:02d}:{:02d}".format(hours, minutes, time))