document.addEventListener("DOMContentLoaded", () => {
    const modal = document.getElementById("exercise-modal");
    const openBtn = document.getElementById("add-exercise-btn");
    const closeBtn = document.querySelector(".close");
    const selectedContainer = document.getElementById("selected-exercises");
    const modalList = document.getElementById("modalExerciseList");
  
    openBtn.onclick = () => modal.style.display = "block";
    closeBtn.onclick = () => modal.style.display = "none";
    window.onclick = e => { if (e.target === modal) modal.style.display = "none"; }
  
    // Load from localStorage
    const exercises = JSON.parse(localStorage.getItem("exerciseList")) || [];
  
    exercises.forEach(ex => {
      const item = document.createElement("div");
      item.className = "modal-exercise";
      item.setAttribute("data-name", ex.name);
      item.setAttribute("data-type", ex.type);
      item.innerHTML = `
        <img src="${ex.img}" alt="${ex.name}">
        <div>
          <p><strong>${ex.name}</strong></p>
          <p>${ex.type}</p>
          <button class="select-exercise">Add</button>
        </div>
      `;
      modalList.appendChild(item);
    });
  
    // Delegate event for dynamically added buttons
    modalList.addEventListener("click", (e) => {
      if (e.target.classList.contains("select-exercise")) {
        const parent = e.target.closest(".modal-exercise");
        const name = parent.getAttribute("data-name");
        const type = parent.getAttribute("data-type");
  
        const entry = document.createElement("div");
        entry.className = "exercise-entry";
        entry.innerHTML = `
          <input type="text" value="${name}" readonly />
          <input type="text" value="${type}" readonly />
        `;
  
        selectedContainer.appendChild(entry);
        modal.style.display = "none";
      }
    });
  });
  