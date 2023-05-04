from keras.models import load_model
import numpy as np
import librosa
import pyaudio

model = load_model("keras_model_audio.h5", compile=False)

class_names = ["Background Noise", "Tuan"]


CHUNK_SIZE = 1024
SAMPLE_RATE = 44100
RECORD_SECONDS = 1

p = pyaudio.PyAudio()

stream = p.open(format=pyaudio.paInt16,
                channels=1,
                rate=SAMPLE_RATE,
                input=True,
                frames_per_buffer=CHUNK_SIZE)

while True:
    # Read the audio data from the stream
    data = stream.read(CHUNK_SIZE)

    # Convert the binary data to a NumPy array
    data = np.frombuffer(data, dtype=np.int16)

    # Compute the spectrogram
    spec = librosa.feature.melspectrogram(y=data, sr=SAMPLE_RATE, n_mels=128,
                                           fmax=8000, hop_length=512)

    # Reshape the spectrogram to match the input shape of the model
    spec = spec.reshape(1, spec.shape[0], spec.shape[1], 1)

    # Make a prediction using the model
    prediction = model.predict(spec)

    # Print the prediction
    print(prediction)



# # Make a prediction
# prediction = model.predict(audio_data)
# index = np.argmax(prediction)
# class_name = class_names[index]
# confidence_score = prediction[0][index]

# # Print prediction and confidence score
# print("Class:", class_name[2:], end=" ")
# print("Confidence Score:", str(np.round(confidence_score * 100))[:-2], "%")

# # Print the prediction
