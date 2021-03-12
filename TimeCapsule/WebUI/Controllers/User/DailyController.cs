using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using TimeCapsule.Application.TimeDailies.Queries.GetDailies;
using TimeCapsule.Application.TimeDailies.Queries.GetDaily;

namespace TimeCapsule.WebUI.Controllers.User
{
    
    public class DailyController : UserBaseController
    {
        [HttpGet]
        public async Task<ActionResult<DailiesVm>> GetDailyList([FromQuery]int skip = 0, [FromQuery]int take = 20)
        {
            return await Mediator.Send(new GetDailiesQuery { Username = GetUsername(), Skip = skip, Take = take});
        }

        [HttpGet("{id}")]
        public async Task<ActionResult<DailyVm>> GetDaily(Guid id)
        {
            return await Mediator.Send(new GetDailyQuery { Username = GetUsername(), Id = id});
        }
    }
}