//성공 토스트 ui
function successToast(message) {
    let toastContainer = document.getElementById('toastContainer');
    let toast = document.createElement('div');
    toast.classList.add('successToast');
    toast.textContent = message;
    
    toastContainer.appendChild(toast);
    
    setTimeout(() => {
        toast.classList.add('show');
    }, 100);

    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => toast.remove(), 500);
    }, 2000);
}

//실패 토스트 ui
function failToast(message) {
    let toastContainer = document.getElementById('toastContainer');
    let toast = document.createElement('div');
    toast.classList.add('failToast');
    toast.textContent = message;
    
    toastContainer.appendChild(toast);
    
    setTimeout(() => { 
        toast.classList.add('show');
    }, 100);

    setTimeout(() => {
        toast.classList.remove('show');
        setTimeout(() => toast.remove(), 500);
    }, 2000);
}