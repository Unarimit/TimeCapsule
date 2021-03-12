using MediatR;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.DependencyInjection;
using TimeCapsule.WebUI.Controllers.Base;

namespace TimeCapsule.WebUI.Controllers.Admin
{
    [Route("api/admin/[controller]")]
    [ApiExplorerSettings(GroupName = "admin")]
    [Authorize("admin")]
    public class AdminBaseController : ApiBaseController
    {

    }
}
