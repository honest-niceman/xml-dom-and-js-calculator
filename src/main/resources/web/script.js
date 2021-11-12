const but = document.inputData.calcName;
but.addEventListener("click", calcHandler);

function calcHandler(e) {
    if ((document.getElementById('firstValue').value === "")
        || (document.getElementById('secondValue').value === "")) {
        alert('Надо заполнить все поля операндов!');
        e.preventDefault();
    } else {
        const firstValue = document.getElementById('firstValue').value;
        const secondValue = document.getElementById('secondValue').value;
        const reg = new RegExp('[+-]?\\d+(\\.\\d+)?');
        const result1 = reg.test(firstValue);
        const result2 = reg.test(secondValue);
        if (!result1 || !result2) {
            alert("Вводите только числа!");
            e.preventDefault();
        }
        if(secondValue == 0.0){
            alert("На ноль делить нельзя");
            e.preventDefault();
        }
    }
}

function getCalc() {
    const p_url = location.search.substring(1);
    const parameters = p_url.split("&"); // получим массив данных без знака &
    const values = [];
    let t = 0;
    for (let i in parameters) {
        const j = parameters[i].split("=");
        values[t++] = j[1];
    }
    const firstValue = values[0];
    const secondValue = values[1];

    const operation = decodeURIComponent(values[2]);
    let result;
    if (operation === "+") {
        result = parseFloat(firstValue) + parseFloat(secondValue);
    } else if (operation === "-") {
        result = firstValue - secondValue;
    } else if (operation === "*") {
        result = firstValue * secondValue;
    } else if (operation === "/") {
        result = firstValue / secondValue;
    }

    document.getElementById("firstValue").innerHTML = firstValue;
    document.getElementById("secondValue").innerHTML = secondValue;
    document.getElementById('operation').innerHTML = operation;
    document.getElementById("result").innerHTML = result;
}


