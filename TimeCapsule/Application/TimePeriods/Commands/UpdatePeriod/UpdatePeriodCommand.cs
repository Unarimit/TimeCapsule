using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Entities;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using TimeCapsule.Application.Common.Model;
using System;
using TimeCapsule.Domain.Enums;

namespace TimeCapsule.Application.TimePeriods.Commands.UpdatePeriod
{
    public class UpdatePeriodCommand : IRequest<CommonResult>
    {
        public string Username { get; set; }

        public Guid Id { get; set; }

        public Guid TaskId { get; set; }

        public TimePeriodAssess Assess { get; set; }

        public bool IsFinish { get; set; }

        public DateTime BeginTime { get; set; }

        public DateTime? EndTime { get; set; }
    }

    public class UpdatePeriodCommandHandler : IRequestHandler<UpdatePeriodCommand, CommonResult>
    {
        private readonly IApplicationDbContext _context;

        public UpdatePeriodCommandHandler(IApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<CommonResult> Handle(UpdatePeriodCommand request, CancellationToken cancellationToken)
        {
            // TODO: implement it later
            throw new System.NotImplementedException();
        }
    }
}
