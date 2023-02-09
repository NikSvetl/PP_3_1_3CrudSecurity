function editUser() {

    function selectRole(role) {
        let roles = [];
        if (role.indexOf("USER") >= 0) {
            roles.push({id: 2,
                name:'ROLE_USER'});
        }
        if (role.indexOf("ADMIN") >= 0) {
            roles.push({id: 1,
                name: 'ROLE_ADMIN'});
        }
        return roles;
    }

    let role = selectRole(Array.from(document.getElementById("editRoles").selectedOptions).map(r => r.value));

    let id = window.formEditUser.editID.value;

    fetch('/api/users', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            id: window.formEditUser.editID.value,
            name: window.formEditUser.editName.value,
            lastName: window.formEditUser.editLastName.value,
            age: window.formEditUser.editAge.value,
            email: window.formEditUser.editEmail.value,
            password: window.formEditUser.editPassword.value,
            roles: role
        }),
        headers: {"Content-type": "application/json; charset=UTF-8"}
    })
        .then(response => {
            $('#' + id).replaceWith(`<tr id="${id}">
                <td> ${id} </td>
                <td> ${window.formEditUser.editName.value} </td>
                <td> ${window.formEditUser.editLastName.value} </td>
                <td> ${window.formEditUser.editAge.value} </td>
                <td> ${window.formEditUser.editEmail.value} </td>
                <td> ${roleName(role)} </td>
                <td> <button type="button" onclick="modalEdit(${id})" class="btn btn-primary">Edit</button> </td>
                <td> <button type="button" onclick="modalDelete(${id})" class="btn btn-danger">Delete</button> </td>
                </tr>`);
        });
}
function roleName(editRole) {
    let roleName = "";
    for (let i = 0; i < editRole.length; i++) {
        let role = editRole[i].name + " ";
        roleName += role;
    }
    return roleName;
}

function modalEdit(id) {

    fetch('/api/users/' + id)
        .then(response => response.json())
        .then(user => {

            let adminSelect = "";
            let userSelect = "";

            for (let i = 0; i < user.roles.length; i++) {
                if (user.roles[i].role === "ROLE_ADMIN") {
                    adminSelect = "selected";
                }
                if (user.roles[i].role === "ROLE_USER") {
                    userSelect = "selected";
                }
            }

            let modal = document.getElementById('modalWindow');

            modal.innerHTML = `
                <div id="modalEdit"
                     class="modal fade" tabindex="-1" role="dialog"
                     aria-labelledby="TitleModalLabel" aria-hidden="true"
                     data-backdrop="static" data-keyboard="false">
                    <div class="modal-dialog modal-dialog-scrollable">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="TitleModalLabel">Edit user</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body bg-white">
                                <form id="formEditUser"
                                       class="form-signin mx-auto font-weight-bold text-center">
                                    <p>
                                        <label>ID</label>
                                        <input class="form-control form-control" type="text"
                                               id="editID" name="id" value="${user.id}" readonly>
                                    </p>
                                    <p>
                                        <label>Name</label>
                                        <input class="form-control form-control" type="text"
                                               id="editName" value="${user.name}"
                                               placeholder="Name" required>
                                    </p>
                                    <p>
                                        <label>Last name</label>
                                        <input class="form-control form-control" type="text"
                                               id="editLastName" value="${user.lastName}"
                                               placeholder="Last name" required>
                                    </p>
                                    <p>
                                        <label>Age</label>
                                        <input class="form-control form-control" type="text"
                                               id="editAge" value="${user.age}" 
                                               placeholder="Age" required>
                                    </p>
                                    <p>
                                        <label>Email</label>
                                        <input class="form-control form-control" type="email"
                                               id="editEmail" value="${user.email}"
                                               placeholder="Username" required>
                                    </p>
                                    <p>
                                        <label>Password</label>
                                        <input class="form-control form-control" type="password"
                                               id="editPassword" placeholder="Password">
                                    </p>
                                    <p>
                            <label for="editRoles" class="font-weight-bolder">Role</label>
                            <div>
                                
                                <select id="editRoles" class="custom select col-sm-4" multiple size="2" required>
                                    <option value="USER">ROLE_USER</option>
                                    <option value="ADMIN">ROLE_ADMIN</option>
                                </select>
                                    </p>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary"
                                        data-dismiss="modal">Close</button>
                                <button class="btn btn-primary" data-dismiss="modal"
                                        onclick="editUser()">Edit</button>
                            </div>
                        </div>
                    </div>
                </div>`;

            $("#modalEdit").modal();

        });
}