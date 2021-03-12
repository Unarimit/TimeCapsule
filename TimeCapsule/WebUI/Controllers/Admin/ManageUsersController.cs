using Microsoft.AspNetCore.Mvc;
using System;
using System.Threading.Tasks;
using TimeCapsule.Application.Users.Queries.AdminGetUserList;

namespace TimeCapsule.WebUI.Controllers.Admin
{
    public class ManageUsersController : AdminBaseController
    {
        [HttpGet]
        public async Task<ActionResult<AdminGetUserListVm>> GetUsers()
        {
            return await Mediator.Send(new AdminGetUserListQuery());
        }
    }
}
