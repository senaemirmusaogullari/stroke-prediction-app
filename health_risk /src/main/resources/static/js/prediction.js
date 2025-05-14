document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("healthForm");
  const loading = document.getElementById("loading");
  const token = localStorage.getItem("token");

  if (!token) {
      alert("Giriş yapmalısınız.");
      window.location.href = "login.html";
      return;
  }

  document.getElementById("logoutBtn")?.addEventListener("click", () => {
      localStorage.clear();
      window.location.href = "login.html";
  });

  form.addEventListener("submit", async function (e) {
      e.preventDefault();

      if (!form.checkValidity()) {
          form.classList.add("was-validated");
          return;
      }

      const healthData = {
          gender: parseInt(document.getElementById("gender").value),
          age: parseFloat(document.getElementById("age").value),
          hypertension: parseInt(document.getElementById("hypertension").value),
          heartDisease: parseInt(document.getElementById("heart_disease").value),
          everMarried: parseInt(document.getElementById("ever_married").value),
          residenceType: parseInt(document.getElementById("residence_type").value),
          avgGlucoseLevel: parseFloat(document.getElementById("avg_glucose_level").value),
          bmi: parseFloat(document.getElementById("bmi").value),

          workTypeGovtJob: 0,
          workTypeNeverWorked: 0,
          workTypePrivate: 0,
          workTypeSelfEmployed: 0,
          workTypeChildren: 0,

          smokingStatusFormerlySmoked: 0,
          smokingStatusNeverSmoked: 0,
          smokingStatusSmokes: 0,
          smokingStatusUnknown: 0
      };

      const workType = document.getElementById("work_type").value;
      healthData[`workType${workType.charAt(0).toUpperCase() + workType.slice(1)}`] = 1;

      const smokingStatus = document.getElementById("smoking_status").value;
      const key = `smokingStatus${smokingStatus.charAt(0).toUpperCase() + smokingStatus.slice(1)}`;
      healthData[key] = 1;

      try {
          if (loading) loading.style.display = "block";

          const response = await fetch("http://localhost:8080/api/health-risk", {
              method: "POST",
              headers: {
                  "Content-Type": "application/json",
                  "Authorization": `Bearer ${token}`
              },
              body: JSON.stringify(healthData)
          });

          const result = await response.json();

          if (!response.ok) {
              throw new Error(result?.error || `Sunucu hatası: ${response.status}`);
          }

          localStorage.setItem("healthRiskResult", JSON.stringify(result));
          window.location.href = "result.html";

      } catch (error) {
          alert("Tahmin sırasında hata oluştu: " + error.message);
      } finally {
          if (loading) loading.style.display = "none";
      }
  });
});