using Microsoft.AspNetCore.Mvc;
using NSwag.Annotations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TimeCapsule.Application.RegisterRequests.Commands;

namespace TimeCapsule.WebUI.Controllers.Identify
{
    public class RegisterController : IdentifyBaseController
    {
        [HttpPost]
        [ProducesResponseType(200)]
        public async Task<ActionResult> CreateRegisterRequest([FromBody]CreateRegisterRequestCommand command)
        {
            await Mediator.Send(command);
            return NoContent();
        }
    }
}
