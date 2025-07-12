document.addEventListener('DOMContentLoaded', function() {
    // Tornar o bloco de exercícios clicável
    const exercisesFeature = document.getElementById('exercises-feature');
    const WorkoutF = document.getElementById('Workout-feature');
    const FavouriteF = document.getElementById('Favourites');
    const RecordF = document.getElementById('Record-feature');
    
    if (exercisesFeature) {
        exercisesFeature.addEventListener('click', function() {
            window.location.href = 'Exercises.html';
        });
        
        // Adiciona feedback visual ao clicar
        exercisesFeature.addEventListener('mousedown', function() {
            this.style.transform = 'translateY(0) scale(0.98)';
        });
        
        exercisesFeature.addEventListener('mouseup', function() {
            this.style.transform = 'translateY(-5px) scale(1)';
        });
        
        exercisesFeature.addEventListener('mouseleave', function() {
            this.style.transform = '';
        });
    }

    if (WorkoutF) {
        WorkoutF.addEventListener('click', function() {
            window.location.href = 'WorkoutPlans.html';
        });
        
        // Adiciona feedback visual ao clicar
        WorkoutF.addEventListener('mousedown', function() {
            this.style.transform = 'translateY(0) scale(0.98)';
        });
        
        WorkoutF.addEventListener('mouseup', function() {
            this.style.transform = 'translateY(-5px) scale(1)';
        });
        
        WorkoutF.addEventListener('mouseleave', function() {
            this.style.transform = '';
        });
    }

    if (FavouriteF) {
        FavouriteF.addEventListener('click', function() {
            window.location.href = 'Favourites.html';
        });
        
        // Adiciona feedback visual ao clicar
        FavouriteF.addEventListener('mousedown', function() {
            this.style.transform = 'translateY(0) scale(0.98)';
        });
        
        FavouriteF.addEventListener('mouseup', function() {
            this.style.transform = 'translateY(-5px) scale(1)';
        });
        
        FavouriteF.addEventListener('mouseleave', function() {
            this.style.transform = '';
        });
    }

    if (RecordF) {
        RecordF.addEventListener('click', function() {
            window.location.href = 'RecordPlans.html';
        });
        
        // Adiciona feedback visual ao clicar
        RecordF.addEventListener('mousedown', function() {
            this.style.transform = 'translateY(0) scale(0.98)';
        });
        
        RecordF.addEventListener('mouseup', function() {
            this.style.transform = 'translateY(-5px) scale(1)';
        });
        
        RecordF.addEventListener('mouseleave', function() {
            this.style.transform = '';
        });
    }
});