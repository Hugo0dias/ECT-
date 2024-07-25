document.addEventListener('DOMContentLoaded', () => {
    let slider = document.querySelector('.slider .list');
    let items = document.querySelectorAll('.slider .item');
    let next = document.getElementById('next');
    let prev = document.getElementById('prev');
    let dots = document.querySelectorAll('.slider .dots li');

    let lengthItems = items.length - 1;
    let active = 0;

    next.onclick = function() {
        active = active + 1 <= lengthItems ? active + 1 : 0;
        reloadSlider();
    };

    prev.onclick = function() {
        active = active - 1 >= 0 ? active - 1 : lengthItems;
        reloadSlider();
    };

    let refreshInterval = setInterval(() => { next.click(); }, 10000);

    function reloadSlider() {
        slider.style.left = -items[active].offsetLeft + 'px';

        let lastActiveDot = document.querySelector('.slider .dots li.active');
        if (lastActiveDot) {
            lastActiveDot.classList.remove('active');
        }
        dots[active].classList.add('active');

        clearInterval(refreshInterval);
        refreshInterval = setInterval(() => { next.click(); }, 10000);
    }

    dots.forEach((li, key) => {
        li.addEventListener('click', () => {
            active = key;
            reloadSlider();
        });
    });

    window.onresize = function() {
        reloadSlider();
    };

    reloadSlider();
});
