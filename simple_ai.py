import cv2
import numpy as np
import base64
import torch
from yolov5.utils.general import non_max_suppression

# Disable scientific notation for clarity
np.set_printoptions(suppress=True)

# Load the model
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
model = torch.hub.load('ultralytics/yolov5', 'custom', path='best.pt')

# Load the labels
class_names = ["with_mask", "without_mask", "mask_weared_incorrect"]

# CAMERA can be 0 or 1 based on default camera of your computer
# camera = cv2.VideoCapture(0)
# camera_droid = cv2.VideoCapture('http://192.168.0.4:4747/video')

camera = cv2.VideoCapture(0)

def image_detector():
    ret, image = camera.read()

    # read camera
    # Resize the raw image into (224-height,224-width) pixels
    image = cv2.resize(image, (224, 224), interpolation=cv2.INTER_AREA)
    # Show the image in a window
    # cv2.imshow("Webcam Image", image)

    res, frame = cv2.imencode('.jpg', image)
    data = base64.b64encode(frame)

    if len(data) > 102400:
        print("image is too big!")
        print(len(data))
    else:
        print("Publish image: ")
        print(len(data))

    # Make the image a numpy array and convert from BGR to RGB
    image = np.asarray(image, dtype=np.float32)[:, :, ::-1]

    # Normalize the image array
    image /= 255.0
    
    copyImage = image.copy()

    # Convert to a PyTorch tensor
    image = torch.from_numpy(copyImage.transpose(2, 0, 1)).unsqueeze(0).float()

    # Predicts the model
    prediction = model(image.to(device))[0]
    prediction = torch.unsqueeze(prediction, 0)
    prediction = non_max_suppression(prediction, conf_thres=0.5, iou_thres=0.5)[0]

    # Print prediction and confidence score
    if prediction is not None and len(prediction) > 0:
        class_id = int(prediction[0][-1])
        class_name = class_names[class_id]
        confidence_score = float(prediction[0][4])
        print("Class:", class_name, end="")
        print("Confidence Score:", str(np.round(confidence_score * 100))[:-2], "%")
        return class_name, data
    else:
        print("No object detected")
        return "No object detected", None
