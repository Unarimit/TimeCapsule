using System;
using System.Collections.Generic;
using System.Text;
using TimeCapsule.Domain.Entities;

namespace TimeCapsule.Application.AndroidSync.AndroidDtos
{
    public class ATimeTaskClass
    {
        public Guid Id { get; set; }

        public string Color { get; set; }

        public string Name { get; set; }

        public TimeTaskClass ToTimeTaskClass(User user)
        {
            return new TimeTaskClass { Id = Id, Color = Color, Name = Name, User = user, LastModified = DateTime.Now };
        }
    }
}
