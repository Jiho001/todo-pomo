document.addEventListener('DOMContentLoaded', function() {
    const modal = document.getElementById("new-todo-modal");
    const btn = document.getElementById("new-todo-button");
    const span = document.getElementsByClassName("close")[0];

    btn.onclick = function() {
        modal.style.display = "block";
    }

    span.onclick = function() {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }

    document.getElementById('add-category-button').onclick = function() {
        const categoryDiv = document.getElementById('categories');
        const newCategoryDiv = document.createElement('div');
        newCategoryDiv.setAttribute('class', 'category-input');

        const newCategoryInput = document.createElement('input');
        newCategoryInput.setAttribute('type', 'text');
        newCategoryInput.setAttribute('class', 'category');
        newCategoryInput.setAttribute('name', 'category');

        const removeCategoryButton = document.createElement('button');
        removeCategoryButton.setAttribute('type', 'button');
        removeCategoryButton.setAttribute('class', 'remove-category-button');
        removeCategoryButton.innerText = 'x';

        removeCategoryButton.onclick = function() {
            categoryDiv.removeChild(newCategoryDiv);
        };

        newCategoryDiv.appendChild(newCategoryInput);
        newCategoryDiv.appendChild(removeCategoryButton);
        categoryDiv.appendChild(newCategoryDiv);
    };

    document.getElementById('new-todo-form').onsubmit = function(event) {
        event.preventDefault();
        const name = document.getElementById('name').value;
        const dueDate = document.getElementById('due-date').value;
        const status = document.getElementById('status').value;

        const categoryInputs = document.getElementsByClassName('category');
        const categories = [];
        for (let i = 0; i < categoryInputs.length; i++) {
            if (categoryInputs[i].value) {
                categories.push({ todoName: categoryInputs[i].value });
            }
        }

        const newTodo = {
            todoName: name,
            dueDate: dueDate,
            status: status,
            categories: categories
        };

        fetch('/api/todos', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(newTodo)
        })
            .then(response => response.json())
            .then(data => {
                modal.style.display = "none";
                fetchTodos(); // 새 Todo를 추가한 후 목록을 다시 가져옴
            })
            .catch(error => console.error('Error:', error));
    };

    document.getElementById('filter-button').onclick = function() {
        fetchTodos();
    };

    function fetchTodos() {
        const categoryFilter = document.getElementById('category-filter').value;
        const statusFilter = document.getElementById('status-filter').value;

        let query = '/api/todos?'; // 올바른 URL 경로로 설정
        if (categoryFilter) {
            query += `category=${categoryFilter}&`;
        }
        if (statusFilter) {
            query += `status=${statusFilter}&`;
        }

        fetch(query)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                // data가 단일 객체임을 가정하고 처리
                const tbody = document.getElementById('todo-table').getElementsByTagName('tbody')[0];
                tbody.innerHTML = ''; // 기존 테이블 내용을 초기화

                // 응답이 단일 객체인 경우
                if (data) {
                    const row = tbody.insertRow();
                    row.insertCell(0).innerText = data.name;
                    row.insertCell(1).innerText = data.dueDate;
                    row.insertCell(2).innerText = data.categories.map(category => category.name).join(', ');
                    row.insertCell(3).innerText = data.status || ''; // taskStatus가 null인 경우 빈 문자열 처리
                }
            })
            .catch(error => console.error('Error:', error));
    }


    fetchTodos();
});
