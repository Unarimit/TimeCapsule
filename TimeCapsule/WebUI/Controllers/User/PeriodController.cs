using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR;
using System;
using System.Threading.Tasks;
using TimeCapsule.Application.Common.Model;
using TimeCapsule.Application.TimePeriods.Commands.CreatePeriod;
using TimeCapsule.Application.TimePeriods.Commands.DeletePeriod;
using TimeCapsule.Application.TimePeriods.Commands.FinishPeriod;
using TimeCapsule.Application.TimePeriods.Commands.UpdatePeriod;
using TimeCapsule.Application.TimePeriods.Queries.GetCurrentPeriod;
using TimeCapsule.Application.TimePeriods.Queries.GetPeriods;
using TimeCapsule.WebUI.Hubs;

namespace TimeCapsule.WebUI.Controllers.User
{
    public class PeriodController : UserBaseController
    {
        IHubContext<SyncHub> _hub;
        public PeriodController(IHubContext<SyncHub> hub) : base()
        {
            _hub = hub;
        }

        /// <summary>
        /// 获取指定日期的时间段
        /// </summary>
        /// <param name="calendar"></param>
        /// <returns></returns>
        [HttpGet("calendar/{calendar}")]
        public async Task<ActionResult<PeriodsVm>> GetPeriodList(int calendar)
        {
            return await Mediator.Send(new GetPeriodsQuery { Username = GetUsername(), Calendar = calendar });
        }

        /// <summary>
        /// 获取当前正在进行的时间段
        /// </summary>
        /// <param name="calendar"></param>
        /// <returns></returns>
        [HttpGet("current")]
        public async Task<ActionResult<PeriodVm>> GetCurrentPeriod()
        {
            return await Mediator.Send(new GetCurrentPeriodQuery { Username = GetUsername()});
        }


        [HttpPost]
        public async Task<ActionResult<CommonResult>> CreatePeriod(CreatePeriodCommand command)
        {
            command.Username = GetUsername();
            var result = await Mediator.Send(command);
            await _hub.Clients.All.SendAsync("ReceiveMessage");
            return result;
        }


        [HttpPut("{id}")]
        public async Task<ActionResult<CommonResult>> UpdatePeriod(Guid id, UpdatePeriodCommand command)
        {
            command.Username = GetUsername();
            command.Id = id;
            var result = await Mediator.Send(command);
            await _hub.Clients.All.SendAsync("ReceiveMessage");
            return result;
        }

        [HttpPut("finish/{id}")]
        public async Task<ActionResult<CommonResult>> FinishPeriod(Guid id, FinishPeriodCommand command)
        {
            command.Username = GetUsername();
            command.Id = id;
            var result = await Mediator.Send(command);
            await _hub.Clients.All.SendAsync("ReceiveMessage");
            return result;
        }

        [HttpDelete("{id}")]
        public async Task<ActionResult<CommonResult>> DeletePeriod(Guid id)
        {
            return await Mediator.Send(new DeletePeriodCommand{ Username = GetUsername(), Id = id });
        }
    }
}
