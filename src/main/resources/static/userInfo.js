userInfoPage();

function userInfoPage() {
    let tableUser = $('#userInfo');
    let userBar = $('#userAuthor');

    fetch('/api/users/name')
        .then(res => res.json())
        .then(user => {
            let htmlUserInfo = `$(
            <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.lastName}</td>
                    <td>${user.age}</td>
                    <td>${user.email}</td>
                    <td>${user.rolesToString}</td>
            </tr>)`;
            tableUser.append(htmlUserInfo)

            let htmlPrincipal = `
                <span class="navbar-brand" href="#">
                ${user.email} with roles: 
                ${user.rolesToString}
                </span> `;
            userBar.append(htmlPrincipal);
        });
}