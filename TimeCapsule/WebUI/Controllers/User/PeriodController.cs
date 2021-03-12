using Microsoft.AspNetCore.Mvc;
using System;
using System.Threading.Tasks;
using TimeCapsule.Application.Common.Model;
using TimeCapsule.Application.TimePeriods.Commands.CreatePeriod;
using TimeCapsule.Application.TimePeriods.Commands.DeletePeriod;
using TimeCapsule.Application.TimePeriods.Commands.FinishPeriod;
using TimeCapsule.Application.TimePeriods.Commands.UpdatePeriod;
using TimeCapsule.Application.TimePeriods.Queries.GetPeriods;

namespace TimeCapsule.WebUI.Controllers.User
{
    public class PeriodController : UserBaseController
    {
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

        [HttpPost]
        public async Task<ActionResult<CommonResult>> CreatePeriod(CreatePeriodCommand command)
        {
            command.Username = GetUsername();
            return await Mediator.Send(command);
        }


        [HttpPut("{id}")]
        public async Task<ActionResult<CommonResult>> UpdatePeriod(Guid id, UpdatePeriodCommand command)
        {
            command.Username = GetUsername();
            command.Id = id;
            return await Mediator.Send(command);
        }

        [HttpPut("finish/{id}")]
        public async Task<ActionResult<CommonResult>> FinishPeriod(Guid id, FinishPeriodCommand command)
        {
            command.Username = GetUsername();
            command.Id = id;
            return await Mediator.Send(command);
        }

        [HttpDelete("{id}")]
        public async Task<ActionResult<CommonResult>> DeletePeriod(Guid id)
        {
            return await Mediator.Send(new DeletePeriodCommand{ Username = GetUsername(), Id = id });
        }
    }
}
