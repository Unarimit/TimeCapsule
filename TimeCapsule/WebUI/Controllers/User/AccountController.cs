using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Security.Claims;
using System.Threading.Tasks;
using TimeCapsule.Application.Users.Queries;

namespace TimeCapsule.WebUI.Controllers.User
{
    public class AccountController : UserBaseController
    {
        [HttpGet]
        public string Test()
        {
            return GetUsername();
        }
    }
}
