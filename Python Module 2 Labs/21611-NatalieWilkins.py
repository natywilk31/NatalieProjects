hour = int(input("Starting time (hours): "))
mins = int(input("Starting time (minutes): "))
dura = int(input("Event duration (minutes): "))
end_minutes = (dura + mins) % 60
end_hours= ((dura+mins) // 60 + hour)%24
#print("The end value of hours will be:" , end_hours)
#print("The end value of minutes will be:" , end_minutes)
print("The end time will be " , end_hours , ":" , end_minutes, sep="")
