document.addEventListener("DOMContentLoaded", function () {
  const currentPlan = JSON.parse(localStorage.getItem("currentPlan"));

  if (!currentPlan) {
    alert("No record plan found. Please select a plan first.");
    window.location.href = "RecordPlans.html"; // Redirect to Record Plans page
    return;
  }

  const planTitleElement = document.getElementById("plan-title");
  planTitleElement.textContent = currentPlan.title;

  const exerciseGrid = document.getElementById("exercise-grid");
  currentPlan.exercises.forEach((exercise) => {
    const exerciseCard = document.createElement("div");
    exerciseCard.className = "exercise-card";

    // Helper function to generate dates spaced by one week
    const generateWeeklyDates = (startDate, count) => {
      const dates = [];
      const start = new Date(startDate);
      for (let i = 0; i < count; i++) {
        const date = new Date(start);
        date.setDate(start.getDate() + i * 7); // Increment by 7 days
        dates.push(date.toISOString().split("T")[0]); // Format as YYYY-MM-DD
      }
      return dates;
    };

    // Generate random progress data for both graph types
    const weeklyDates = generateWeeklyDates("2025-04-01", 5); // Start on April 1, 2025, with 5 weeks

    const overallData = weeklyDates.map((date) => ({
      value: Math.floor(Math.random() * 100),
      details: [
        { set: "Set1", weight: "20kg", notes: "Good form" },
        { set: "Set2", weight: "30kg", notes: "Felt strong" },
        { set: "Set3", weight: "40kg", notes: "Challenging" },
      ],
      date,
    }));

    const weightData = weeklyDates.map((date) => ({
      value: Math.floor(Math.random() * 200),
      details: [
        { set: "Set1", weight: "50kg", notes: "Heavy lift" },
        { set: "Set2", weight: "60kg", notes: "Good effort" },
        { set: "Set3", weight: "70kg", notes: "Maxed out" },
      ],
      date,
    }));

    const svgWidth = 800;
    const svgHeight = 300;

    const createGraph = (data) => {
      const maxValue = Math.max(...data.map((d) => d.value));
      const points = data.map((d, index) => {
        const x = (index / (data.length - 1)) * svgWidth; // Evenly distribute points along X-axis
        const y = svgHeight - (d.value / maxValue) * svgHeight;
        return { x, y, details: d.details, date: d.date };
      });

      const polylinePoints = points.map((p) => `${p.x},${p.y}`).join(" ");

      const circles = points
        .map(
          (p) => `
          <circle cx="${p.x}" cy="${p.y}" r="6" fill="#468201" data-details='${JSON.stringify(
            p.details
          )}' data-date="${p.date}" />
        `
        )
        .join("");

      return `
        <polyline points="${polylinePoints}" fill="none" stroke="#468201" stroke-width="3" />
        ${circles}
      `;
    };

    exerciseCard.innerHTML = `
      <div class="exercise-info">
        <h2>${exercise.name}</h2>
        <img src="${exercise.img}" alt="${exercise.name}" class="exercise-image">
      </div>
      <div class="graph-container">
        
        <div class="graph-menu">
          <label for="graph-type">Select Graph:</label>
          <select class="graph-type" onchange="updateGraph(this)">
            <option value="overall">Overall Status</option>
            <option value="weight">Weight Status</option>
          </select>
        </div>
        <svg class="line-graph" width="${svgWidth}" height="${svgHeight}">
          ${createGraph(overallData)}
        </svg>
        <div class="tooltip" style="display: none;"></div>
        <div class="graph-label y-axis">Progress</div>
        <div class="graph-label x-axis">Time</div>
      </div>
    `;
    exerciseCard.dataset.overallData = JSON.stringify(overallData);
    exerciseCard.dataset.weightData = JSON.stringify(weightData);

    exerciseGrid.appendChild(exerciseCard);
  });

  // Add event listeners for tooltips
  document.querySelectorAll(".line-graph circle").forEach((circle) => {
    circle.addEventListener("mouseenter", (e) => {
      const tooltip = e.target.closest(".graph-container").querySelector(".tooltip");
      const details = JSON.parse(e.target.getAttribute("data-details"));
      const date = e.target.getAttribute("data-date");

      tooltip.innerHTML = `
        <strong>Date:</strong> ${date}<br>
        ${details
          .map((d) => `<strong>${d.set}:</strong> ${d.weight} (${d.notes})`)
          .join("<br>")}
      `;
      tooltip.style.display = "block";
      tooltip.style.left = `${e.offsetX + 10}px`;
      tooltip.style.top = `${e.offsetY - 40}px`;
    });

    circle.addEventListener("mouseleave", (e) => {
      const tooltip = e.target.closest(".graph-container").querySelector(".tooltip");
      tooltip.style.display = "none";
    });
  });
});

// Function to update the graph based on the selected type
function updateGraph(selectElement) {
  const graphContainer = selectElement.closest(".graph-container");
  const exerciseCard = graphContainer.closest(".exercise-card");
  const svg = graphContainer.querySelector(".line-graph");

  const overallData = JSON.parse(exerciseCard.dataset.overallData);
  const weightData = JSON.parse(exerciseCard.dataset.weightData);

  const selectedType = selectElement.value;
  const data = selectedType === "overall" ? overallData : weightData;

  const svgWidth = 600;
  const svgHeight = 300;
  const maxValue = Math.max(...data.map((d) => d.value));
  const points = data
    .map((d, index) => {
      const x = (index / (data.length - 1)) * svgWidth;
      const y = svgHeight - (d.value / maxValue) * svgHeight;
      return { x, y, details: d.details, date: d.date };
    });

  const polylinePoints = points.map((p) => `${p.x},${p.y}`).join(" ");

  const circles = points
    .map(
      (p) => `
      <circle cx="${p.x}" cy="${p.y}" r="6" fill="#468201" data-details='${JSON.stringify(
        p.details
      )}' data-date="${p.date}" />
    `
    )
    .join("");

  svg.innerHTML = `
    <polyline points="${polylinePoints}" fill="none" stroke="#468201" stroke-width="3" />
    ${circles}
  `;

  // Reattach tooltip event listeners for the updated graph
  svg.querySelectorAll("circle").forEach((circle) => {
    circle.addEventListener("mouseenter", (e) => {
      const tooltip = e.target.closest(".graph-container").querySelector(".tooltip");
      const details = JSON.parse(e.target.getAttribute("data-details"));
      const date = e.target.getAttribute("data-date");

      tooltip.innerHTML = `
        <strong>Date:</strong> ${date}<br>
        ${details
          .map((d) => `<strong>${d.set}:</strong> ${d.weight} (${d.notes})`)
          .join("<br>")}
      `;
      tooltip.style.display = "block";
      tooltip.style.left = `${e.offsetX + 10}px`;
      tooltip.style.top = `${e.offsetY - 40}px`;
    });

    circle.addEventListener("mouseleave", (e) => {
      const tooltip = e.target.closest(".graph-container").querySelector(".tooltip");
      tooltip.style.display = "none";
    });
  });
}