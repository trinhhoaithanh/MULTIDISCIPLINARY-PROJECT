from Adafruit_IO import Client

ADAFRUIT_IO_USERNAME = 'hungneet'
ADAFRUIT_IO_KEY = 'aio_dlcY51MWgaVDz7VctVtAnt4wtbdA'

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
            aio.send_data(aio.feeds('button3').key, 1)
            
    if 'light' in text and 'off' in text:
        if 'light' in userPermission[name]:
            aio.send_data(aio.feeds('button3').key, 0)
            
    if 'pump' in text and 'on' in text:
        if 'pump' in userPermission[name]:
            aio.send_data(aio.feeds('button3').key, 1)
            
        
    if 'pump' in text and 'off' in text:
        if 'pump' in userPermission[name]:
            aio.send_data(aio.feeds('button3').key, 1)
            
    if 'temp' in text:
        if 'temp' in userPermission[name]:
            pass
    if 'humidity' in text:
        if 'humidity' in userPermission[name]:
            pass