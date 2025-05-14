from flask import Flask, request, jsonify
from flask_cors import CORS
import joblib
import numpy as np

app = Flask(__name__)
CORS(app)  # CORS desteği

# Modeli yükle
model = joblib.load("xgboost_stroke_model.pkl")

@app.route("/predict", methods=["POST"])
def predict():
    try:
        data = request.get_json()
        print("📥 Gelen veri:", data)

        # Modelin beklediği sırayla veri hazırlığı (15 özellik)
        input_data = [
            data["gender"],
            data["age"],
            data["hypertension"],
            data["heartDisease"],
            data["everMarried"],
            data["residenceType"],
            data["avgGlucoseLevel"],
            data["bmi"],
            data["workTypeNeverWorked"],
            data["workTypePrivate"],
            data["workTypeSelfEmployed"],
            data["workTypeChildren"],
            data["smokingStatusFormerlySmoked"],
            data["smokingStatusNeverSmoked"],
            data["smokingStatusSmokes"]
        ]

        print("📊 Model girdisi:", input_data)

        prediction = model.predict([input_data])[0]
        print("🎯 Tahmin sonucu:", prediction)

        return jsonify({"stroke": int(prediction), "probability": 0.75 if prediction == 1 else 0.22})

    except KeyError as e:
        return jsonify({"error": f"Gerekli alan eksik: {str(e)}"}), 400
    except Exception as e:
        return jsonify({"error": f"Tahmin sırasında hata: {str(e)}"}), 500

if __name__ == "__main__":
    app.run(debug=True)