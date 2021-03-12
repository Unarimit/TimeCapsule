using TimeCapsule.Application.Common.Interfaces;
using TimeCapsule.Domain.Entities;
using MediatR;
using System.Threading;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using TimeCapsule.Application.Common.Model;
using System;

namespace TimeCapsule.Application.TimePeriods.Commands.DeletePeriod
{
    public class DeletePeriodCommand : IRequest<CommonResult>
    {
        public string Username { get; set; }

        public Guid Id { get; set; }
    }
    public class DeletePeriodCommandHandler : IRequestHandler<DeletePeriodCommand, CommonResult>
    {
        private readonly IApplicationDbContext _context;

        public DeletePeriodCommandHandler(IApplicationDbContext context)
        {
            _context = context;
        }

        public async Task<CommonResult> Handle(DeletePeriodCommand request, CancellationToken cancellationToken)
        {
            var period = await _context.Periods
                .Include(x => x.Task)
                .ThenInclude(x => x.User)
                .FirstOrDefaultAsync(x => x.Id == request.Id);

            if (period == null)
                return CommonResult.Fail("period do not exist");

            if (period.Task.User.Username != request.Username)
                return CommonResult.Fail("error user");

            _context.Periods.Remove(period);

            await _context.SaveChangesAsync(cancellationToken);
            return CommonResult.SUCCESS;
        }
    }
}
