document.addEventListener("DOMContentLoaded", () => {
    const createTodoBtn = document.getElementById("create-todo-btn");
    const todoModal = document.getElementById("todo-modal");
    const closeModalBtn = document.getElementById("close-modal-btn");
    const saveTodoBtn = document.getElementById("save-todo-btn");
    const todoList = document.getElementById("todo-list");
    const filterBtn = document.getElementById("filter-btn");

    // 모달 창 열기
    createTodoBtn.addEventListener("click", () => {
        todoModal.classList.add("active");
    });

    // 모달 창 닫기
    closeModalBtn.addEventListener("click", () => {
        todoModal.classList.remove("active");
    });

    saveTodoBtn.addEventListener("click", async () => {
        const name = document.getElementById("todo-name").value;
        const dueDate = document.getElementById("todo-due-date").value;
        const status = document.getElementById("todo-status").value;
        const categoriesInput = document.getElementById("todo-categories").value;

        const categories = categoriesInput.split(',').map(cat => cat.trim()).filter(cat => cat);

        // 상태 변환
        let statusCode;
        if (status === "진행중") {
            statusCode = "ONGOING";
        } else if (status === "완료") {
            statusCode = "COMPLETED";
        } else if (status === "대기") {
            statusCode = "WAITING";
        }

        const newTodo = {
            name,
            dueDate,
            status: statusCode,
            categories: categories.map(name => ({ name }))
        };

        // 서버에 투두 생성 요청
        try {
            const response = await fetch('/api/todos', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(newTodo)
            });

            if (response.ok) {
                const createdTodo = await response.json();
                addTodoToDOM(createdTodo);
                todoModal.classList.remove("active");
            } else {
                console.error('투두 생성 실패');
            }
        } catch (error) {
            console.error('에러 발생:', error);
        }
    });



    // 필터 적용
    filterBtn.addEventListener("click", async () => {
        const category = document.getElementById("category-filter").value;
        const status = document.getElementById("status-filter").value;

        try {
            let url = '/api/todos?';
            if (category) url += `category=${encodeURIComponent(category)}&`;
            if (status) url += `status=${encodeURIComponent(status)}&`;

            const response = await fetch(url);
            if (response.ok) {
                const todos = await response.json();
                renderTodoList(todos);
            } else {
                console.error('필터링 실패');
            }
        } catch (error) {
            console.error('에러 발생:', error);
        }
    });

    // 투두 리스트 DOM에 추가
    function addTodoToDOM(todo) {
        const item = document.createElement("div");
        item.className = "todo-item";
        item.innerHTML = `
            <h3>${todo.name}</h3>
            <p>마감기한: ${todo.dueDate}</p>
            <p>상태: ${todo.taskStatus || 'N/A'}</p>
            <p>카테고리: ${todo.categories.map(cat => cat.name).join(', ')}</p>
        `;
        todoList.appendChild(item);
    }

    // 투두 리스트 렌더링
    function renderTodoList(todos) {
        todoList.innerHTML = '';
        todos.forEach(addTodoToDOM);
    }
});
