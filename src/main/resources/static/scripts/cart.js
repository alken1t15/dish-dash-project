const plusButtons = document.querySelectorAll('.cart-item__btn[data-action="plus"]');
const minusButtons = document.querySelectorAll('.cart-item__btn[data-action="minus"]');
const deleteButtons = document.querySelectorAll('.delete');
const cartList = document.querySelector('.cart-list');
const totalAmountElement = document.getElementById('totalAmount');
const emptyMessageElement = document.querySelector('.empty-message');

function handlePlusButtonClick(event) {
    updateQuantity(event.target.parentNode, 1);
}

function handleMinusButtonClick(event) {
    updateQuantity(event.target.parentNode, -1);
}

function handleDeleteButtonClick(event) {
    const cartItem = event.target.closest('.cart-item');
    axios.post('http://localhost:8080/cart/edit', {
        "id": event.target.closest('.cart-item').getAttribute("data-index"),
        "setting": 'delete'
    })
        .then(function (response) {
            console.log(response);
        })
        .catch(function (error) {
            console.log(error);
        });
    cartList.removeChild(cartItem);
    calculateTotal();
    checkEmptyCart();
}

async function updateQuantity(container, change) {
    const quantityElement = container.querySelector('.quantity .number');
    let quantity = parseInt(quantityElement.innerHTML);

    if (quantity + change >= 0 && quantity + change <= 20) {
        quantity += change;
        quantityElement.textContent = quantity;

        console.log(change)

        if(change == "-1"){
            axios.post('http://localhost:8080/cart/edit', {
                "id": event.target.closest('.cart-item').getAttribute("data-index"),
                "setting": 'minus'
            })
                .then(function (response) {
                    console.log(response);
                })
                .catch(function (error) {
                    console.log(error);
                });
        }
        else{
            axios.post('http://localhost:8080/cart/edit', {
                "id": event.target.closest('.cart-item').getAttribute("data-index"),
                "setting": 'plus'
            })
                .then(function (response) {
                    console.log(response);
                })
                .catch(function (error) {
                    console.log(error);
                });
        }

        calculateTotal();
    }

    if (quantity === 0) {
        const cartItem = container.closest('.cart-item');
        cartList.removeChild(cartItem);
        axios.post('http://localhost:8080/cart/edit', {
            "id": event.target.closest('.cart-item').getAttribute("data-index"),
            "setting": 'delete'
        })
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            });
        checkEmptyCart();
    }
}

function calculateTotal() {
    let total = 0;
    const cartItems = document.querySelectorAll('.cart-item');

    cartItems.forEach(item => {
        const priceElement = item.querySelector('.price-text-number');
        const quantityElement = item.querySelector('.quantity .number');
        const price = parseFloat(priceElement.textContent);
        const quantity = parseInt(quantityElement.textContent);

        total += price * quantity;
    });

    totalAmountElement.textContent = total;
}

function checkEmptyCart() {
    const cartItems = document.querySelectorAll('.cart-item');
    const take = document.querySelector('.cartBtn');
    const total = document.querySelector('.total');
    const col = document.querySelector('.columns');
    const cartTitle = document.querySelector('.cart__title');
    const btnBack = document.querySelector('.bb');

    if (cartItems.length === 0) {
        emptyMessageElement.style.display = 'block';
        take.style.display = 'none';
        total.style.display = 'none';
        col.style.display = 'none';
        cartTitle.style.display = 'none';
        btnBack.style.display = 'none';

    } else {
        emptyMessageElement.style.display = 'none';
    }
}

plusButtons.forEach(button => {
    button.addEventListener('click', handlePlusButtonClick);
});

minusButtons.forEach(button => {
    button.addEventListener('click', handleMinusButtonClick);
});

deleteButtons.forEach(button => {
    button.addEventListener('click', handleDeleteButtonClick);
});

calculateTotal();
checkEmptyCart();