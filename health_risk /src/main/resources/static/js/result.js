document.addEventListener("DOMContentLoaded", function () {
    const resultArea = document.getElementById("resultArea");
    const token = localStorage.getItem("token");

    if (!token) {
        window.location.href = "login.html";
        return;
    }

    const storedResult = localStorage.getItem("healthRiskResult");

    if (storedResult) {
        const data = JSON.parse(storedResult);
        displayResult(data);
        localStorage.removeItem("healthRiskResult");
    } else {
        resultArea.innerHTML = "<p class='text-danger'>Sonuç bulunamadı. Lütfen tekrar deneyin.</p>";
    }

    document.getElementById("logoutBtn")?.addEventListener("click", function () {
        localStorage.clear();
        window.location.href = "login.html";
    });

    function displayResult(data) {
        const {
            age, gender, hypertension, heartDisease, everMarried,
            workType, residenceType, avgGlucoseLevel, bmi, smokingStatus,
            stroke, probability
        } = data;

        const riskLevel = stroke === 1
            ? `<span class="text-danger fw-bold">⚠️ İnme riski VAR!</span>`
            : `<span class="text-success fw-bold">✅ İnme riski YOK.</span>`;

        const riskPercent = probability ? Math.round(probability * 100) : (stroke === 1 ? 75 : 10);

        resultArea.innerHTML = `
            <p><strong>Yaş:</strong> ${age}</p>
            <p><strong>Cinsiyet:</strong> ${gender}</p>
            <p><strong>Hipertansiyon:</strong> ${hypertension === 1 ? "Var" : "Yok"}</p>
            <p><strong>Kalp Hastalığı:</strong> ${heartDisease === 1 ? "Var" : "Yok"}</p>
            <p><strong>Evli mi:</strong> ${everMarried}</p>
            <p><strong>İş Türü:</strong> ${workType}</p>
            <p><strong>Yaşam Alanı:</strong> ${residenceType}</p>
            <p><strong>Ortalama Glikoz Seviyesi:</strong> ${avgGlucoseLevel} mg/dL</p>
            <p><strong>BMI:</strong> ${bmi}</p>
            <p><strong>Sigara Kullanımı:</strong> ${smokingStatus}</p>
            <hr>
            <h4>${riskLevel} (%${riskPercent})</h4>
            <canvas id="riskChart" width="300" height="300" class="mt-4"></canvas>
        `;

        drawChart(riskPercent);
    }

    function drawChart(riskPercent) {
        const ctx = document.getElementById('riskChart').getContext('2d');

        new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: ['İnme Riski', 'Düşük Risk'],
                datasets: [{
                    data: [riskPercent, 100 - riskPercent],
                    backgroundColor: ['#dc3545', '#28a745'],
                    borderWidth: 2
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'bottom'
                    }
                }
            }
        });
    }
});