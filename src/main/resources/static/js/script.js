    function addToCart(productId) {
        fetch('/cart/add/' + productId, {
            method: 'POST'
        }).then(response => {
            if (response.ok) {
                alert('Товар добавлен в корзину!');
            } else {
                alert('Ошибка при добавлении товара в корзину.');
            }
        });
    }

    function applySort() {
        const sortOption = document.getElementById('sort').value;
        document.getElementById('sortId').value = sortOption;
        document.getElementById('sortForm').submit();
    }

    function toggleFilters() {
        const panel = document.getElementById('filterPanel');
        const overlay = document.getElementById('overlay');
        panel.classList.toggle('active');
        overlay.classList.toggle('active');
    }

     // Обновляем метки ползунков
    const minPriceSlider = document.getElementById('minPrice');
    const maxPriceSlider = document.getElementById('maxPrice');
    const minPriceLabel = document.getElementById('minPriceLabel');
    const maxPriceLabel = document.getElementById('maxPriceLabel');
    const minPriceInput = document.getElementById('minPriceInput');
    const maxPriceInput = document.getElementById('maxPriceInput');

    minPriceSlider.oninput = function() {
        minPriceLabel.textContent = minPriceSlider.value;
        minPriceInput.value = minPriceSlider.value;
    };

    maxPriceSlider.oninput = function() {
        maxPriceLabel.textContent = maxPriceSlider.value;
        maxPriceInput.value = maxPriceSlider.value;
    };

    minPriceInput.oninput = function() {
        minPriceSlider.value = minPriceInput.value;
        minPriceLabel.textContent = minPriceInput.value;
    };

    maxPriceInput.oninput = function() {
        maxPriceSlider.value = maxPriceInput.value;
        maxPriceLabel.textContent = maxPriceInput.value;
    };

    // Функция для отправки фильтров
    function applyFilters() {
        const minPrice = document.getElementById('minPrice').value;
        const maxPrice = document.getElementById('maxPrice').value;

        // Извлекаем categoryId из атрибута data-category-id
        const categoryId = document.getElementById('categoryId').getAttribute('data-category-id');

        // Отправка фильтров через форму
        const form = document.createElement('form');
        form.method = 'post';
        form.action = '/category/' + categoryId + '/filter';

        const minPriceInput = document.createElement('input');
        minPriceInput.type = 'hidden';
        minPriceInput.name = 'minPrice';
        minPriceInput.value = minPrice;

        const maxPriceInput = document.createElement('input');
        maxPriceInput.type = 'hidden';
        maxPriceInput.name = 'maxPrice';
        maxPriceInput.value = maxPrice;

        form.appendChild(minPriceInput);
        form.appendChild(maxPriceInput);
        document.body.appendChild(form);
        form.submit();
    }

