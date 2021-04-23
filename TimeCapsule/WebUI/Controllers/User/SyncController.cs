using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using TimeCapsule.Application.AndroidSync.Commands.SyncPeriods;
using TimeCapsule.Application.AndroidSync.Commands.SyncStuff;
using TimeCapsule.Application.AndroidSync.Queries.GetPeriods;
using TimeCapsule.WebUI.Hubs;

namespace TimeCapsule.WebUI.Controllers.User
{
    public class SyncController : UserBaseController
    {
        IHubContext<SyncHub> _hub;
        public SyncController(IHubContext<SyncHub> hub) : base()
        {
            _hub = hub;
        }

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

        /// <summary>
        /// sync push periods
        /// </summary>
        /// <param name="command"></param>
        /// <returns></returns>
        [HttpPost("periods")]
        public async Task<ActionResult<SyncPeriodsVm>> SyncPeriod(SyncPeriodsCommand command)
        {
            var result = await Mediator.Send(command);
            await _hub.Clients.All.SendAsync("ReceiveMessage");
            return result;
        }

        /// <summary>
        /// sync pull periods
        /// </summary>
        /// <param name="command"></param>
        /// <returns></returns>
        [HttpPost("getPeriods")]
        public async Task<ActionResult<PeriodsVm>> QueryStuff(GetPeriodsQuery command)
        {
            return await Mediator.Send(command);
        }
    }
}
