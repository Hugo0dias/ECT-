// language-dropdown.js
document.addEventListener('DOMContentLoaded', function() {
    const languageOptions = document.querySelectorAll('.language-option');
    
    if (languageOptions) {
      languageOptions.forEach(option => {
        option.addEventListener('click', function(e) {
          e.preventDefault();
          const lang = this.getAttribute('data-lang');
          
          // Aqui você pode adicionar a lógica para mudar o idioma
          console.log('Idioma selecionado:', lang);
          
          // Exemplo: mostrar qual idioma foi selecionado
          alert(`Idioma mudado para: ${lang === 'pt' ? 'Português' : 'English'}`);
          
          // Você pode adicionar aqui a lógica real para mudar o idioma do site
          // Por exemplo: window.location.href = `?lang=${lang}`;
        });
      });
    }
  });