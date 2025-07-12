// exercise-sort.js
document.addEventListener('DOMContentLoaded', function() {
    // Espera o DOM estar totalmente carregado
    const sortSelect = document.getElementById('sortExercises');
    
    if (sortSelect) {
      sortSelect.addEventListener('change', sortExerciseList);
    }
    
    function sortExerciseList() {
      const sortBy = this.value;
      const exerciseList = document.querySelector('.exercise-list');
      
      if (!exerciseList) return;
      
      const exercises = Array.from(exerciseList.children);
      
      exercises.sort((a, b) => {
        if (sortBy === 'number') {
          const numA = parseInt(a.querySelector('.exercise-number').textContent.replace('No.', '').trim();
          const numB = parseInt(b.querySelector('.exercise-number').textContent.replace('No.', '').trim();
          return numA - numB;
        } else {
          const nameA = a.querySelector('.exercise-name').textContent.toLowerCase().trim();
          const nameB = b.querySelector('.exercise-name').textContent.toLowerCase().trim();
          return nameA.localeCompare(nameB);
        }
      });
  
      // Limpar a lista e adicionar os itens ordenados
      while (exerciseList.firstChild) {
        exerciseList.removeChild(exerciseList.firstChild);
      }
      
      exercises.forEach(exercise => exerciseList.appendChild(exercise));
    }
  });