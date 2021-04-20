using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TimeCapsule.Application.AndroidSync.Commands.SyncStuff;

namespace TimeCapsule.WebUI.Controllers.User
{
    public class SyncController : UserBaseController
    {
        /// <summary>
        /// sync stuff include task and taskclass
        /// </summary>
        /// <param name="command"></param>
        /// <returns></returns>
        [HttpPost("stuff")]
        public async Task<ActionResult<SyncStuffVm>> SyncStuff(SyncStuffCommand command)
        {
            command.SetUsername(GetUsername());
            return await Mediator.Send(command);
        }
    }
}
