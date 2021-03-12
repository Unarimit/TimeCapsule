using Microsoft.AspNetCore.Mvc;
using System;
using System.Threading.Tasks;
using TimeCapsule.Application.Common.Model;
using TimeCapsule.Application.TimeTaskClasses.Commands.CreateTaskClass;
using TimeCapsule.Application.TimeTaskClasses.Commands.DeleteTaskClass;
using TimeCapsule.Application.TimeTaskClasses.Commands.UpdateTaskClass;
using TimeCapsule.Application.TimeTaskClasses.Queries.GetTaskClasses;

namespace TimeCapsule.WebUI.Controllers.User
{
    public class TaskClassController : UserBaseController
    {

        [HttpGet]
        public async Task<ActionResult<TaskClassesVm>> GetTaskClasses()
        {
            return await Mediator.Send(new GetTaskClassesQuery() { Username = GetUsername() });
        }

        [HttpPost]
        public async Task<ActionResult<CommonResult>> CreateTaskClass(CreateTaskClassCommand command)
        {
            command.Username = GetUsername();
            return await Mediator.Send(command);
        }

        [HttpPut("{id}")]
        public async Task<ActionResult<CommonResult>> UpdateTaskClass(Guid id, UpdateTaskClassCommand command)
        {
            command.Id = id;
            command.Username = GetUsername();
            return await Mediator.Send(command);
        }

        [HttpDelete("{id}")]
        public async Task<ActionResult<CommonResult>> DeleteTaskClass(Guid id)
        {
            return await Mediator.Send(new DeleteTaskClassCommand { Username = GetUsername(), Id = id });
        }
    }
}
