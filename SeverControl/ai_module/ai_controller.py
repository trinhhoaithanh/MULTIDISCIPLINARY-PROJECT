from keras.models import load_model
from adafruit.ada_controller import AdaController
import cv2
import numpy as np
from simple_ai import *

np.set_printoptions(suppress=True)

class AiController:
    # static fields
    # model = load_model("./ai_module/keras_model.h5", compile=False)
    # class_names = open("./ai_module/labels.txt", "r").readlines()
    
    current_ai_data = ''

    @classmethod
    def ai_detector(cls):
        """Ai to detect mask

        Returns:
            ai_class: return ai class from image_detector
            data: image from openCV
        """
        return image_detector()
    @classmethod
    def voice_detector():
        """Ai to clasify voice
        """
        pass
    @classmethod
    def update_ai(cls, client, count):
        if count == AdaController.sensor_frequency:
            numMask, numNoMask, data = cls.ai_detector()
            aiImage = str(numMask) + ' with mask ' + str(numNoMask) + ' without mask'
            if aiImage != cls.current_ai_data:
                client.publish("ai", aiImage)
                print("Face data: ", aiImage)
            client.publish("image", data)
            cls.current_ai_data = data
