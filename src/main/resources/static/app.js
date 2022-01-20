const url = 'http://localhost:8080/admin/users'

loadUserTable()

// подгрузка таблицы
function loadUserTable() {
    let table = $('#AllUsers tbody');
    table.empty();

    fetch(url)
        .then(res => res.json())
        .then(data => {
            $.each(data, function (i, user) {
                let tr = `$(
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.lastname}</td>     
                    <td>${user.email}</td>     
                    <td>${user.age}</td>     
                    <td>${user.roles.map(roles => roles.name)}</td>     
                    <td>
                        <button onclick="editUserById(${user.id})" data-action="edit" class="btn btn-primary" 
                        data-toggle="modal" data-target="#editModal">Edit
                        </button>
                    </td>
                    <td>
                        <button onclick="deleteUserById(${user.id})" data-action="delete" class="btn btn-danger" 
                        data-toggle="modal" data-target="#deleteModal">Delete
                        </button>
                    </td>
                </tr>
                )`;
                table.append(tr);
            })
        })
}

//добавление юзера
function createUser() {
    let roles = () => {
        let array = []
        let options = document.querySelector('#createRoles').options
        for (let i = 0; i < options.length; i++) {
            if (options[i].selected) {
                let role = {
                    id: options[i].value,
                    name: options[i].text
                }
                array.push(role)
            }
        }
        return array
    }
    let createUser = {
        username: document.getElementById('createUsername').value,
        lastname: document.getElementById('createLastname').value,
        age: document.getElementById('createAge').value,
        email: document.getElementById('createEmail').value,
        password: document.getElementById('createPassword').value,
        roles: roles()
    }
    console.log(createUser)

    fetch(url, {
        method: 'POST',
        headers: {'Content-type': 'application/json; charset=utf-8'},
        body: JSON.stringify(createUser)
    }).then(res => res.json()).then(() => {
        loadUserTable();
        $('#admin-tab').tab('show');
    })
}

//модалка редактирования
function editUserById(id) {
    fetch(url + '/' + id, {method: "GET", dataType: 'json'})
        .then(res => {
            res.json().then(user => {
                $('#editId').val(user.id)
                $('#editUsername').val(user.username)
                $('#editLastname').val(user.lastname)
                $('#editAge').val(user.age)
                $('#editEmail').val(user.email)
                $('#editPassword').val(user.password)
                $('#editRole').val(user.roles)
            })
        })
}

function editButton() {
    let roles = () => {
        let array = []
        let option = document.querySelector('#editRole')
        for (let i = 0; i < option.length; i++) {
            if (option[i].selected) {
                let role = {
                    id: option[i].value,
                    name: option[i].text
                }
                array.push(role)
            }
        }
        return array
    }
    let editUser = {
        id: document.getElementById('editId').value,
        username: document.getElementById('editUsername').value,
        lastname: document.getElementById('editLastname').value,
        age: document.getElementById('editAge').value,
        email: document.getElementById('editEmail').value,
        password: document.getElementById('editPassword').value,
        roles: roles()
    }
    console.log(editUser)

    fetch(url, {
        method: 'PUT',
        headers: {'Content-type': 'application/json; charset=utf-8'},
        body: JSON.stringify(editUser)
    }).then(res => res.json()).then(() => {
        loadUserTable();
        $('#editModal').modal('hide');
    })
}

//модалка удаления
function deleteUserById(id) {
    fetch(url + "/" + id, {method: 'GET', dataType: 'json'})
        .then(res => {
            res.json().then(user => {
                $('#deleteId').val(user.id)
                $('#deleteUsername').val(user.username)
                $('#deleteLastname').val(user.lastname)
                $('#deleteAge').val(user.age)
                $('#deleteEmail').val(user.email)
                $('#deletePassword').val(user.password)
                $('#deleteRoles').val(user.roles)
            })
        })
}

function deleteButton() {
    let deleteUser = {
        id: document.getElementById('deleteId').value,
    }

    fetch(url, {
        method: 'DELETE',
        headers: {'Content-Type': 'application/json; charset=utf-8'},
        body: JSON.stringify(deleteUser)
    }).then(() => {
        loadUserTable()
        $('#deleteModal').modal('hide')
    })
}