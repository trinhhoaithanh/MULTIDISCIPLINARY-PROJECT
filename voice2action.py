from Adafruit_IO import Client

ADAFRUIT_IO_USERNAME = 'clowz'
ADAFRUIT_IO_KEY = 'aio_GfBy85NpAYNTCNbiWnoSGUcvXJ4m'

aio = Client(ADAFRUIT_IO_USERNAME, ADAFRUIT_IO_KEY)

# right for each person
userPermission = {'Tuan': {'light'}, 
                  'Thien': {'pump'}, 
                  'Thanh': {'temp'},
                  'Hung': {'humidity'}}

def actionOnPermission(text, name):
    aio.send_data(aio.feeds('voice').key, text)
    if 'light' in text and 'on' in text:
        if 'light' in userPermission[name]:
            aio.send_data(aio.feeds('nutnhan1').key, 1)
            
    if 'light' in text and 'off' in text:
        if 'light' in userPermission[name]:
            aio.send_data(aio.feeds('nutnhan1').key, 0)
            
    if 'pump' in text and 'on' in text:
        if 'pump' in userPermission[name]:
            aio.send_data(aio.feeds('nutnhan2').key, 1)
            
        
    if 'pump' in text and 'off' in text:
        if 'pump' in userPermission[name]:
            aio.send_data(aio.feeds('nutnhan2').key, 0)
            
    if 'temp' in text:
        if 'temp' in userPermission[name]:
            pass
    if 'humidity' in text:
        if 'humidity' in userPermission[name]:
            pass