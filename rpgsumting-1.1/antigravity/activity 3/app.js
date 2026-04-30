// ==========================================
// Application State & Theme Logic
// ==========================================

// Theme Toggling (HCI Accessibility & Creativity)
const themeToggle = document.getElementById('themeToggle');
const rootElement = document.documentElement;
const themeIcon = themeToggle.querySelector('i');

themeToggle.addEventListener('click', () => {
    const currentTheme = rootElement.getAttribute('data-theme');
    if (currentTheme === 'light') {
        rootElement.setAttribute('data-theme', 'dark');
        themeIcon.classList.replace('fa-moon', 'fa-sun');
        showToast('Dark Mode Enabled', 'info');
    } else {
        rootElement.setAttribute('data-theme', 'light');
        themeIcon.classList.replace('fa-sun', 'fa-moon');
        showToast('Light Mode Enabled', 'info');
    }
});

// Mobile Search Toggle
const searchToggle = document.querySelector('.search-toggle');
const mobileSearch = document.getElementById('mobileSearch');

if (searchToggle) {
    searchToggle.addEventListener('click', () => {
        if (mobileSearch.style.display === 'block') {
            mobileSearch.style.display = 'none';
        } else {
            mobileSearch.style.display = 'block';
            mobileSearch.querySelector('input').focus();
        }
    });
}

// Mobile Filter Sidebar Toggle
const filterToggleBtn = document.getElementById('filterToggle');
const filtersSidebar = document.getElementById('filtersSidebar');
if (filterToggleBtn && filtersSidebar) {
    filterToggleBtn.addEventListener('click', () => {
        if (filtersSidebar.style.display === 'block') {
            filtersSidebar.style.display = 'none';
        } else {
            filtersSidebar.style.display = 'block';
        }
    });
}

// Slider Display Logic
const priceSlider = document.getElementById('priceSlider');
const priceVal = document.getElementById('priceVal');

if (priceSlider) {
    priceSlider.addEventListener('input', (e) => {
        priceVal.textContent = e.target.value;
        renderProducts(); // trigger live filtering
    });
}

// "Shop Collection" Button Scroll
const shopNowBtn = document.getElementById('shopNowBtn');
if (shopNowBtn) {
    shopNowBtn.addEventListener('click', () => {
        document.querySelector('.products-section').scrollIntoView({ behavior: 'smooth' });
    });
}

// ==========================================
// Product Data & Rendering (Functionality)
// ==========================================
const products = [
    { id: 1, title: 'Aura QuietX Headphones', category: 'electronics', price: 299.99, img: 'https://images.unsplash.com/photo-1618366712010-f4ae9c647dcb?w=400&q=80' },
    { id: 2, title: 'Classic Cotton T-Shirt', category: 'apparel', price: 24.99, img: 'https://images.unsplash.com/photo-1521572163474-6864f9cf17ab?w=400&q=80' },
    { id: 3, title: 'Ceramic Coffee Mug', category: 'home', price: 14.99, img: 'https://images.unsplash.com/photo-1514228742587-6b1558fcca3d?w=400&q=80' },
    { id: 4, title: 'Polarized Sunglasses', category: 'accessories', price: 89.99, img: 'https://images.unsplash.com/photo-1511499767150-a48a237f0083?w=400&q=80' },
    { id: 5, title: 'Aura Studio Pod', category: 'electronics', price: 149.99, img: 'https://images.unsplash.com/photo-1608043152269-423dbba4f392?w=400&q=80' },
    { id: 6, title: 'Leather Crossbody Bag', category: 'accessories', price: 119.99, img: 'https://images.unsplash.com/photo-1548036328-c9fa89d128fa?w=400&q=80' },
    { id: 7, title: 'Denim Jacket', category: 'apparel', price: 79.99, img: 'https://images.unsplash.com/photo-1576871337622-98d48d1cf531?w=400&q=80' },
    { id: 8, title: 'Cast Iron Skillet', category: 'home', price: 49.99, img: 'https://images.unsplash.com/photo-1585671512411-cf0b5beaf844?w=400&q=80' },
    { id: 9, title: 'Aura TrackBand Lite', category: 'electronics', price: 49.99, img: 'https://images.unsplash.com/photo-1557438159-51eecce89612?w=400&q=80' },
    { id: 10, title: 'Cozy Wool Blanket', category: 'home', price: 59.99, img: 'https://images.unsplash.com/photo-1580142646399-6e3e110cb2bc?w=400&q=80' },
    { id: 11, title: 'Stainless Steel Watch', category: 'accessories', price: 199.99, img: 'https://images.unsplash.com/photo-1524805444758-089113d48a6d?w=400&q=80' },
    { id: 12, title: 'Running Sneakers', category: 'apparel', price: 129.99, img: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?w=400&q=80' }
];

let cart = [];
const productGrid = document.getElementById('productGrid');
const searchInputs = [document.getElementById('searchInput'), document.querySelector('#mobileSearch input')].filter(Boolean);
const categoryCheckboxes = document.querySelectorAll('input[name="category"]');

// Master Filter & Render Function
function renderProducts() {
    if (!productGrid) return;
    productGrid.innerHTML = '';

    // Get current filter states
    const activeCategories = Array.from(categoryCheckboxes).filter(cb => cb.checked).map(cb => cb.value);
    const maxPrice = parseFloat(priceSlider ? priceSlider.value : 500);

    // Get search term from whichever input is actively typed in
    const searchTerm = searchInputs.map(i => i.value).join('').toLowerCase();

    // Filter array
    const filtered = products.filter(p => {
        const matchesSearch = p.title.toLowerCase().includes(searchTerm);
        const matchesCategory = activeCategories.includes(p.category);
        const matchesPrice = p.price <= maxPrice;

        return matchesSearch && (matchesCategory || activeCategories.length === 0) && matchesPrice;
    });

    if (filtered.length === 0) {
        productGrid.innerHTML = '<p style="color:var(--text-secondary); width: 100%; grid-column: 1/-1;">No products found matching your criteria.</p>';
        return;
    }

    filtered.forEach(product => {
        const card = document.createElement('div');
        card.className = 'product-card';
        card.innerHTML = `
            <img src="${product.img}" alt="${product.title}" class="product-image" loading="lazy">
            <div class="product-info">
                <span class="product-category">${product.category}</span>
                <h3 class="product-title">${product.title}</h3>
                <div class="card-footer">
                    <span class="product-price">$${product.price}</span>
                    <button class="add-to-cart-btn" onclick="addToCart(${product.id})" aria-label="Add ${product.title} to cart">
                        <i class="fa-solid fa-plus"></i>
                    </button>
                </div>
            </div>
        `;
        productGrid.appendChild(card);
    });
}

// Initial Render
renderProducts();

// Bind filter events
searchInputs.forEach(input => {
    input.addEventListener('input', (e) => {
        // Keep inputs synced
        searchInputs.forEach(i => { if (i !== e.target) i.value = e.target.value; });
        renderProducts();
    });
});
categoryCheckboxes.forEach(cb => cb.addEventListener('change', renderProducts));

// ==========================================
// Cart Logic & Layout Animations
// ==========================================
const cartToggleBtn = document.getElementById('cartToggle');
const closeCartBtn = document.getElementById('closeCartBtn');
const cartSidebar = document.getElementById('cartSidebar');
const cartOverlay = document.getElementById('cartOverlay');

function updateCartUI() {
    document.getElementById('cartCount').textContent = cart.length;
    const itemsContainer = document.getElementById('cartItemsContainer');
    const totalPriceEl = document.getElementById('cartTotalPrice');
    const checkoutBtn = document.getElementById('checkoutBtn');

    itemsContainer.innerHTML = '';
    let total = 0;

    if (cart.length === 0) {
        itemsContainer.innerHTML = '<p class="empty-cart-msg">Your cart is empty. Start shopping!</p>';
        checkoutBtn.disabled = true;
    } else {
        checkoutBtn.disabled = false;
        cart.forEach((item, index) => {
            total += item.price;
            itemsContainer.innerHTML += `
                <div class="cart-item">
                    <img src="${item.img}" alt="${item.title}">
                    <div class="cart-item-info">
                        <h4>${item.title}</h4>
                        <p>$${item.price}</p>
                        <button onclick="removeFromCart(${index})" style="background:none; border:none; color:var(--danger); cursor:pointer; font-size:0.85rem; margin-top:0.5rem; font-family:'Outfit'">Remove</button>
                    </div>
                </div>
            `;
        });
    }

    totalPriceEl.textContent = `$${total.toFixed(2)}`;
    document.getElementById('checkoutTotal').textContent = `$${total.toFixed(2)}`;
}

window.addToCart = function (productId) {
    const product = products.find(p => p.id === productId);
    if (product) {
        cart.push(product);
        updateCartUI();
        showToast(`${product.title} added to cart!`);
    }
};

window.removeFromCart = function (index) {
    cart.splice(index, 1);
    updateCartUI();
};

function openCart() {
    cartSidebar.classList.add('active');
    cartOverlay.classList.add('active');
}

function closeCart() {
    cartSidebar.classList.remove('active');
    cartOverlay.classList.remove('active');
}

if (cartToggleBtn) cartToggleBtn.addEventListener('click', openCart);
if (closeCartBtn) closeCartBtn.addEventListener('click', closeCart);
if (cartOverlay) cartOverlay.addEventListener('click', closeCart);

// ==========================================
// Checkout Modal Flow
// ==========================================
const checkoutBtn = document.getElementById('checkoutBtn');
const checkoutModal = document.getElementById('checkoutModal');
const cancelCheckoutBtn = document.getElementById('cancelCheckoutBtn');
const confirmOrderBtn = document.getElementById('confirmOrderBtn');

if (checkoutBtn) {
    checkoutBtn.addEventListener('click', () => {
        closeCart();
        cartOverlay.classList.add('active');
        checkoutModal.classList.add('active');
    });
}

if (cancelCheckoutBtn) {
    cancelCheckoutBtn.addEventListener('click', () => {
        checkoutModal.classList.remove('active');
        cartOverlay.classList.remove('active');
    });
}

if (confirmOrderBtn) {
    confirmOrderBtn.addEventListener('click', () => {
        const nameInput = document.getElementById('checkoutName');
        const addressInput = document.getElementById('checkoutAddress');

        if (!nameInput.value.trim() || !addressInput.value.trim()) {
            showToast('Please provide your Full Name and Shipping Address to proceed.', 'error');
            if (!nameInput.value.trim()) nameInput.style.borderColor = 'var(--danger)';
            if (!addressInput.value.trim()) addressInput.style.borderColor = 'var(--danger)';
            return;
        }

        // Reset borders visually
        nameInput.style.borderColor = 'var(--border-color)';
        addressInput.style.borderColor = 'var(--border-color)';

        document.getElementById('checkoutFlowStep1').style.display = 'none';
        document.getElementById('checkoutFlowStep2').style.display = 'block';
        cart = []; // Empty cart
        updateCartUI();
    });
}

document.getElementById('continueShoppingBtn')?.addEventListener('click', () => {
    checkoutModal.classList.remove('active');
    cartOverlay.classList.remove('active');
    setTimeout(() => {
        document.getElementById('checkoutFlowStep1').style.display = 'block';
        document.getElementById('checkoutFlowStep2').style.display = 'none';
    }, 400); // Wait for transition
});

// ==========================================
// Toast Notification System (HCI Feedback)
// ==========================================
function showToast(message, type = 'success') {
    const toastContainer = document.getElementById('toastContainer');
    if (!toastContainer) return;

    const toast = document.createElement('div');
    toast.className = `toast toast-${type}`;

    const icon = type === 'success' ? 'fa-check-circle' : (type === 'error' ? 'fa-circle-xmark' : 'fa-info-circle');
    const color = type === 'success' ? 'var(--success)' : (type === 'error' ? 'var(--danger)' : 'var(--accent)');

    toast.innerHTML = `
        <div style="background: var(--bg-secondary); border-left: 4px solid ${color}; padding: 1rem 1.5rem; border-radius: var(--radius-md); box-shadow: var(--shadow-soft); display: flex; align-items: center; gap: 1rem; margin-top: 1rem; animation: slideInRight 0.3s forwards;">
            <i class="fa-solid ${icon}" style="color: ${color}; font-size: 1.25rem;"></i>
            <span style="font-weight: 500; color: var(--text-primary);">${message}</span>
        </div>
    `;

    toastContainer.appendChild(toast);

    // Auto remove
    setTimeout(() => {
        toast.style.animation = 'fadeOutRight 0.3s forwards';
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}

// Global Inject Keyframes for Toasts
const styleSheet = document.createElement("style");
styleSheet.innerText = `
    .toast-container { position: fixed; bottom: 2rem; right: 2rem; z-index: 10000; }
    @keyframes slideInRight {
        from { transform: translateX(100%); opacity: 0; }
        to { transform: translateX(0); opacity: 1; }
    }
    @keyframes fadeOutRight {
        from { transform: translateX(0); opacity: 1; }
        to { transform: translateX(100%); opacity: 0; }
    }
`;
document.head.appendChild(styleSheet);
