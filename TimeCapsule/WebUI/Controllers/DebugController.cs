using Microsoft.AspNetCore.Mvc;
using System;
using System.Threading.Tasks;
using NSwag.Annotations;
using TimeCapsule.WebUI.Controllers.Base;
using TimeCapsule.Application.Common.Interfaces;
using System.Linq;
using TimeCapsule.Domain.Entities;
using System.Collections.Generic;

namespace TimeCapsule.WebUI.Controllers
{
    [Route("debugapi/[controller]")]
    [ApiExplorerSettings(GroupName = "debug")]
    public class DebugController : ApiBaseController
    {
        private readonly IApplicationDbContext _context;
        public DebugController(IApplicationDbContext context)
        {
            _context = context;
        }
        /// <summary>
        /// 添加不同角色的用户
        /// </summary>
        /// <returns></returns>
        [HttpGet]
        [SwaggerResponse(typeof(Guid))]
        public async Task<ActionResult> AddUsers()
        {
            if (_context.Users.Count() > 0)
                return NoContent();

            var today = DateTime.Now;
            var task1 = new TimeTask
            {
                Name = "英语6级",
                Icon = "abc.png",
                IsFinish = false,
                IsCounting = true,
                DeadLine = new DateTime(today.Year, 12, 18)
            };

            var task2 = new TimeTask
            {
                Name = "上课",
                Icon = "course.png",
                IsFinish = false,
                IsCounting = false
            };

            var task3 = new TimeTask
            {
                Name = "吃饭",
                Icon = "lunch.png",
                IsFinish = false,
                IsCounting = false
            };


            var taskClass1 = new TimeTaskClass
            {
                Name = "学习",
                Color = "#FF6666",
                Tasks = new List<TimeTask>() { task1, task2}
            };

            var taskClass2 = new TimeTaskClass
            {
                Name = "生活",
                Color = "#AAAAAA",
                Tasks = new List<TimeTask>() { task3 }
            };
            var period = new TimePeriod
            {
                Task = task1,
                BeginTime = new DateTime(today.Year, today.Month, today.Day-1, 9, 20, 0),
                EndTime = new DateTime(today.Year, today.Month, today.Day - 1, 11, 30, 0),
                IsFinish = true,
            };

            var period2 = new TimePeriod
            {
                Task = task3,
                BeginTime = new DateTime(today.Year, today.Month, today.Day - 1, 11, 40, 0),
                EndTime = new DateTime(today.Year, today.Month, today.Day - 1, 12, 20, 0),
                IsFinish = true,
            };

            var period3 = new TimePeriod
            {
                Task = task2,
                BeginTime = new DateTime(today.Year, today.Month, today.Day - 1, 14, 20, 0),
                EndTime = new DateTime(today.Year, today.Month, today.Day - 1, 19, 30, 0),
                IsFinish = true,
            };

            var period4 = new TimePeriod
            {
                Task = task3,
                BeginTime = new DateTime(today.Year, today.Month, today.Day, 7, 40, 0),
                EndTime = new DateTime(today.Year, today.Month, today.Day, 8, 00, 0),
                IsFinish = true,
            };
            var period5 = new TimePeriod
            {
                Task = task2,
                BeginTime = new DateTime(today.Year, today.Month, today.Day, 9, 00, 0),
                IsFinish = false,
            };

            var daily = new TimeDaily
            {
                Calendar = int.Parse(new DateTime(today.Year, today.Month, today.Day - 1, 11, 40, 0).ToString("yyyMMdd")),
                Periods = new List<TimePeriod>() { period, period2, period3},
            };

            var daily2 = new TimeDaily
            {
                Calendar = int.Parse(today.ToString("yyyMMdd")),
                Periods = new List<TimePeriod>() { period4, period5 },
            };

            _context.Users.Add(new Domain.Entities.User
            {
                Username = "admin",
                Password = "1234",
                Email = "admin@admin.com",
                IsAdmin = true,
                IsUser = true,
                TaskClasses = new List<TimeTaskClass>() { taskClass1, taskClass2},
                Tasks = new List<TimeTask>() { task1, task2, task3},
                Dailies = new List<TimeDaily>() {daily, daily2 }
            });
            _context.Users.Add(new Domain.Entities.User
            {
                Username = "test",
                Password = "1234",
                Email = "test@test.com",
                IsAdmin = false,
                IsUser = true
            });
            await _context.SaveChangesAsync(new System.Threading.CancellationToken());

            return Ok();
        }
    }
}
