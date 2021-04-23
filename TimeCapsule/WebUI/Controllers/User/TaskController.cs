using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Linq;
using System.Security.Claims;
using System.Threading;
using System.Threading.Tasks;
using TimeCapsule.Application.Common.Model;
using TimeCapsule.Application.TimeTasks.Commands.CreateTask;
using TimeCapsule.Application.TimeTasks.Commands.CreateTaskAndClass;
using TimeCapsule.Application.TimeTasks.Commands.DeleteTask;
using TimeCapsule.Application.TimeTasks.Commands.FinishTask;
using TimeCapsule.Application.TimeTasks.Commands.UpdateTask;
using TimeCapsule.Application.TimeTasks.Queries.GetOftenTasks;
using TimeCapsule.Application.TimeTasks.Queries.GetTasks;

namespace TimeCapsule.WebUI.Controllers.User
{
    public class TaskController : UserBaseController
    {
        [HttpGet]
        public async Task<ActionResult<TasksVm>> GetTaskList([FromQuery]int skip = 0, [FromQuery]int take = 20)
        {
            return await Mediator.Send(new GetTasksQuery { Username = GetUsername(), Skip = skip, Take = take }) ;

        }
        [HttpGet("often")]
        public async Task<ActionResult<OftenTasksVm>> GetOftenTaskList([FromQuery] int skip = 0, [FromQuery] int take = 20)
        {
            return await Mediator.Send(new GetOftenTasksQuery { Username = GetUsername(), Skip = skip, Take = take });
        }

        [HttpPost]
        public async Task<ActionResult<CommonResult>> CreateTask(CreateTaskCommand command)
        {
            command.Username = GetUsername();
            return await Mediator.Send(command);
        }

        [HttpPost("withclass")]
        public async Task<ActionResult<CommonResult>> CreateTaskAndClass(CreateTaskAndClassCommand command)
        {
            command.Username = GetUsername();
            return await Mediator.Send(command);
        }

        [HttpPut("{id}")]
        public async Task<ActionResult<CommonResult>> UpdateTask(Guid id, UpdateTaskCommand command)
        {   
            command.Id = id;
            command.Username = GetUsername();
            return await Mediator.Send(command);
        }

        [HttpPut("finish/{id}")]
        public async Task<ActionResult<CommonResult>> UpdateTaskFinishStatu(Guid id, FinishTaskCommand command)
        {
            command.Id = id;
            command.Username = GetUsername();
            return await Mediator.Send(command);
        }


        [HttpDelete("{id}")]
        public async Task<ActionResult<CommonResult>> DeleteTask(Guid id)
        {
            return await Mediator.Send(new DeleteTaskCommand { Id = id, Username = GetUsername() });
        }
    }
}
