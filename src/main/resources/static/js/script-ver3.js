document.addEventListener("DOMContentLoaded", () => {
    const createTodoBtn = document.getElementById("create-todo-btn");
    const todoModal = document.getElementById("todo-modal");
    const closeModalBtn = document.getElementById("close-modal-btn");
    const saveTodoBtn = document.getElementById("save-todo-btn");
    const todoList = document.getElementById("todo-list");
    const filterBtn = document.getElementById("filter-btn");

    // 수정 모달 관련 변수
    const editTodoModal = document.getElementById("edit-todo-modal");
    const closeEditModalBtn = document.getElementById("close-edit-modal-btn");
    const updateTodoBtn = document.getElementById("update-todo-btn");
    let currentEditTodoId = null; // 수정 중인 투두의 ID 저장


    // 모달 창 열기
    createTodoBtn.addEventListener("click", () => {
        todoModal.classList.add("active");
    });

    // 모달 창 닫기
    closeModalBtn.addEventListener("click", () => {
        todoModal.classList.remove("active");
    });

    closeEditModalBtn.addEventListener("click", () => {
        editTodoModal.classList.remove("active");
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

        let nameColor;
        if (todo.status.name === "진행중") {
            nameColor = "blue";
        } else if (todo.status.name === "완료") {
            nameColor = "green";
        } else {
            nameColor = "black";
        }

        item.innerHTML = `
            <h3 style="color: ${nameColor};">${todo.name}</h3>
            <p>마감기한: ${todo.dueDate}</p>
            <p>상태: ${todo.status.name}</p>
            <p>카테고리: ${todo.categories.map(cat => cat.name).join(', ')}</p>
        `;
        todoList.appendChild(item);

        // "수정" 버튼 클릭 이벤트 추가
        item.querySelector(".edit-todo-btn").addEventListener("click", () => {
            openEditModal(todo);
        });
    }

    // 투두 리스트 렌더링
    function renderTodoList(todos) {
        todoList.innerHTML = '';
        todos.forEach(addTodoToDOM);
    }

    function openEditModal(todo) {
        currentEditTodoId = todo.id;
        document.getElementById("edit-todo-name").value = todo.name;
        document.getElementById("edit-todo-due-date").value = todo.dueDate;
        document.getElementById("edit-todo-status").value = todo.taskStatus;
        document.getElementById("edit-todo-categories").value = todo.categories.map(cat => cat.name).join(', ');
        editTodoModal.classList.add("active");
    }

    updateTodoBtn.addEventListener("click", async () => {
        const name = document.getElementById("edit-todo-name").value;
        const dueDate = document.getElementById("edit-todo-due-date").value;
        const status = document.getElementById("edit-todo-status").value;
        const categoriesInput = document.getElementBy("edit-todo-categories").value;

        const categories = categoriesInput.split(',').map(cat => cat.trim()).filter(cat => cat);

        let statusCode;
        if (status === "진행중") {
            statusCode = "ONGOING";
        } else if (status === "완료") {
            statusCode = "COMPLETED";
        } else if (status === "대기") {
            statusCode = "WAITING";
        }

        const updatedTodo = {
            name,
            dueDate,
            status: statusCode,
            categories: categories.map(name => ({ name }))
        };

        try {
            const response = await fetch(`/api/todos/edit/${currentEditTodoId}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(updatedTodo)
            });

            if (response.ok) {
                const updatedTodoFromServer = await response.json();
                // 리스트를 새로 고침 또는 업데이트
                todoList.innerHTML = ''; // 기존 리스트 지우기
                fetchTodos(); // 새로고침하여 최신 리스트를 가져옴
                editTodoModal.classList.remove("active");
            } else {
                console.error('투두 수정 실패');
            }
        } catch (error) {
            console.error('에러 발생:', error);
        }
    });

    // 투두 리스트를 서버에서 다시 가져오는 함수
    async function fetchTodos() {
        try {
            const response = await fetch('/api/todos');
            if (response.ok) {
                const todos = await response.json();
                renderTodoList(todos);
            } else {
                console.error('투두 목록을 가져오지 못했습니다.');
            }
        } catch (error) {
            console.error('에러 발생:', error);
        }
    }

    // 페이지 로드 시 투두 리스트 불러오기
    fetchTodos();
});
