import tensorflow as tf
import numpy as np
import sounddevice as sd

# Load the TensorFlow Lite model
interpreter = tf.lite.Interpreter(model_path="soundclassifier_with_metadata.tflite")
interpreter.allocate_tensors()

# Get the input and output tensors
input_details = interpreter.get_input_details()
output_details = interpreter.get_output_details()

# Define the class names
class_names = ["Background Noise", "Hung", "Thanh", "Thien", "Tuan"]

# Define the callback function for the microphone input
def audio_callback(indata, frames, time, status):
    # Resample the audio to match the model's sample rate
    resampled_audio = np.interp(np.linspace(0, len(indata), len(indata)), np.arange(0, len(indata)), indata[:, 0]).astype(np.float32)

    # Normalize the audio data to be between -1 and 1
    normalized_audio = (resampled_audio / np.max(np.abs(resampled_audio)))

    # Pad or truncate the audio data to match the expected input shape of the model
    padded_audio = np.pad(normalized_audio, (0, input_details[0]['shape'][1] - len(normalized_audio)), mode='constant')

    # Add a batch dimension to the audio data
    input_data = np.expand_dims(padded_audio, axis=0)

    # Set the input tensor
    interpreter.set_tensor(input_details[0]['index'], input_data)

    # Run the model
    interpreter.invoke()

    # Get the output tensor and convert it to probabilities
    output_data = interpreter.get_tensor(output_details[0]['index'])[0]
    output_probabilities = tf.nn.softmax(output_data).numpy()

    # Get the class with the highest probability
    predicted_class = class_names[np.argmax(output_probabilities)]

    print("Predicted class:", predicted_class)
    print("Probabilities: ", output_probabilities)

# Start the microphone input
with sd.InputStream(callback=audio_callback):
    while True:
        pass