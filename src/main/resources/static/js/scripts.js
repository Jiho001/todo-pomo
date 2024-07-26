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
                categories.push({ name: categoryInputs[i].value });
            }
        }

        const newTodo = {
            name: name,
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

        let query = '/';
        if (categoryFilter) {
            query += `category=${categoryFilter}&`;
        }
        if (statusFilter) {
            query += `status=${statusFilter}&`;
        }

        fetch(query)
            .then(response => response.json())
            .then(data => {
                const tbody = document.getElementById('todo-table').getElementsByTagName('tbody')[0];
                tbody.innerHTML = '';
                data.forEach(todo => {
                    const row = tbody.insertRow();
                    row.insertCell(0).innerText = todo.name;
                    row.insertCell(1).innerText = todo.dueDate;
                    row.insertCell(2).innerText = todo.categories.map(category => category.name).join(', ');
                    row.insertCell(3).innerText = todo.status;
                });
            })
            .catch(error => console.error('Error:', error));
    }

    fetchTodos();
});
