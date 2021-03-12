using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.Security.Claims;
using TimeCapsule.WebUI.Controllers.Base;

namespace TimeCapsule.WebUI.Controllers.User
{
    [Route("api/user/[controller]")]
    [ApiExplorerSettings(GroupName = "user")]
    [Authorize("user")]
    public class UserBaseController : ApiBaseController
    {
        public UserBaseController()
        {
            // _username = User.FindFirstValue(ClaimTypes.Name);
        }

        protected string GetUsername()
        {
            return User.FindFirstValue(ClaimTypes.Name);
        }
    }
}
