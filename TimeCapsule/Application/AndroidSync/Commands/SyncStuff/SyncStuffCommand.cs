using MediatR;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using TimeCapsule.Application.AndroidSync.AndroidDtos;
using TimeCapsule.Application.Common.Exceptions;
using TimeCapsule.Application.Common.Interfaces;

namespace TimeCapsule.Application.AndroidSync.Commands.SyncStuff
{
    public class SyncStuffCommand : IRequest<SyncStuffVm>
    {
        private string username;

        public IList<ATimeTask> ATimeTasks { get; set; }

        public IList<ATimeTaskClass> ATimeTaskClasses { get; set; }

        public void SetUsername(string username)
        {
            this.username = username;
        }

        public string GetUsername()
        {
            return username;
        }

    }

    public class SyncStuffCommandHandler : IRequestHandler<SyncStuffCommand, SyncStuffVm>
    {
        IApplicationDbContext _context;
        public SyncStuffCommandHandler(IApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<SyncStuffVm> Handle(SyncStuffCommand request, CancellationToken cancellationToken)
        {
            var vm = new SyncStuffVm();
            // check user
            var user = await _context.Users.FirstOrDefaultAsync(x => x.Username == request.GetUsername());
            if (user == null)
                throw new UserExceptions.UnExistUserException();

            // add taskClass
            foreach(var taskclass in request.ATimeTaskClasses)
            {
                var temp = await _context.TaskClasses.FirstOrDefaultAsync(x => x.Id == taskclass.Id);
                if(temp == null)
                {
                    _context.TaskClasses.Add(taskclass.ToTimeTaskClass(user));
                }
                else
                {
                    // TODO: check last modified
                    temp = taskclass.ToTimeTaskClass(user);
                }
            }
            vm.TaskClassChange =  await _context.SaveChangesAsync(new CancellationToken());

            // add task
            foreach (var task in request.ATimeTasks)
            {
                var temp = await _context.Tasks.FirstOrDefaultAsync(x => x.Id == task.Id);
                var taskClass = await _context.TaskClasses.FirstOrDefaultAsync(x => x.Id == task.ClassId);
                if(taskClass == null)
                {
                    throw new TaskClassException.TaskClassUnexistException(task.ClassId);
                }
                if (temp == null)
                {
                    _context.Tasks.Add(task.ToTimeTask(taskClass, user));
                }
                else
                {
                    // TODO: check last modified
                    temp = task.ToTimeTask(taskClass, user);
                }
            }
            vm.TaskChange = await _context.SaveChangesAsync(new CancellationToken());

            return vm;
        }
    }
}
