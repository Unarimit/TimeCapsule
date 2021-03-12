using MediatR;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.DependencyInjection;
using TimeCapsule.WebUI.Controllers.Base;

namespace TimeCapsule.WebUI.Controllers.Identify
{
    [Route("api/identify/[controller]")]
    [ApiExplorerSettings(GroupName = "identify")]
    public class IdentifyBaseController : ApiBaseController
    {

    }
}
